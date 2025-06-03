package com.example.harmony.ui.home

import android.content.Context
import com.example.harmony.R
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class HomeViewModel(private val homeModel: HomeModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions {
    data class EmotionDetails(
        val id: String,
        val name: String,
        val iconRes: Int,
        val color: Color,
        val numericalValue: Float
    )

    val feliz = context.getString(R.string.ED_feliz)
    val triste = context.getString(R.string.ED_triste)
    val enojado = context.getString(R.string.ED_enojado)
    val amor = context.getString(R.string.ED_amor)
    val sorprendido = context.getString(R.string.ED_sorprendido)

    val allEmotionsMap: Map<String, EmotionDetails> = listOf(
        EmotionDetails("feliz", feliz, R.drawable.ic_emotion_happy, Color(0xFFFFEB3B), 5f), // Amarillo
        EmotionDetails("triste", triste, R.drawable.ic_emotion_sad, Color(0xFF2196F3), 1f),    // Azul
        EmotionDetails("enojado", enojado, R.drawable.ic_emotion_angry, Color(0xFFFA0000), 3f), // Gris
        EmotionDetails("amor", amor, R.drawable.ic_emotion_love, Color(0xFFFF9800), 2f), // Naranja
        EmotionDetails("sorprendido", sorprendido, R.drawable.ic_emotion_idkbutnothappy, Color(0xFF4CAF50), 4f) // Verde
        // ... Agrega todas tus emociones
    ).associateBy { it.id }

    private val db = FirebaseFirestore.getInstance()

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    private val _weeklySummary = MutableStateFlow<List<DailyEmotionSummary>>(emptyList())
    val weeklySummary: StateFlow<List<DailyEmotionSummary>> = _weeklySummary.asStateFlow()

    private val _monthlySummary = MutableStateFlow<List<MonthlyEmotionDataPoint>>(emptyList())
    val monthlySummary: StateFlow<List<MonthlyEmotionDataPoint>> = _monthlySummary.asStateFlow()

    private val _isLoadingSummaries = MutableStateFlow(false)
    val isLoadingSummaries: StateFlow<Boolean> = _isLoadingSummaries.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    private val timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    init {
        cargarApodoEnDrawerContent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadEmotionSummaries()
        }
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = homeModel.cargarApodoEnDrawerContent(context, db)
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        viewModelScope.launch {
            // Eliminar el apodo del caché
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey("nickname"))
                preferences.remove(stringPreferencesKey("email"))
            }

            FirebaseAuth.getInstance().signOut()
            kotlinx.coroutines.delay(100)

            navController.navigate("login") {
                popUpTo(navController.graph.findStartDestination().id) { // Pop up to the start of the graph
                    inclusive = true
                }
                launchSingleTop = true // Asegura que no haya múltiples "login"
            }
        }
    }

    override fun uploadProfileImage(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadEmotionSummaries() {
        viewModelScope.launch {
            _isLoadingSummaries.value = true
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
                _isLoadingSummaries.value = false
                _weeklySummary.value = emptyList()
                _monthlySummary.value = emptyList()
                return@launch
            }

            try {
                val now = LocalDate.now()
                val emotionsCollection = db.collection("users").document(userId).collection("emotions")

                // Acá formateamos la fecha para la consulta
                val querySnapshot = emotionsCollection
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .await()

                val allFetchedEmotions = querySnapshot.documents.mapNotNull { doc ->
                    val emotionData = doc.toObject(EmotionEntry::class.java)
                    emotionData?.let {
                        try {
                            val dateTime = LocalDateTime.parse(it.timestamp, timestampFormatter)
                            Pair(dateTime, it)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }

                processWeeklySummary(allFetchedEmotions, now)
                processMonthlySummary(allFetchedEmotions, now)

            } catch (e: Exception) {
                e.printStackTrace()
                _weeklySummary.value = emptyList()
                _monthlySummary.value = emptyList()
            } finally {
                _isLoadingSummaries.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processWeeklySummary(entries: List<Pair<LocalDateTime, EmotionEntry>>, today: LocalDate) {
        val sunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val saturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))

        // Filtra las entradas que caen dentro de la semana actual (domingo a sábado)
        val currentWeekEntries = entries.filter {
            val entryDate = it.first.toLocalDate() // Convierte LocalDateTime a LocalDate para comparar fechas
            !entryDate.isBefore(sunday) && !entryDate.isAfter(saturday)
        }

        val domingoAbreviado = context.getString(R.string.domingoAbreviado)
        val lunesAbreviado = context.getString(R.string.lunesAbreviado)
        val martesAbreviado = context.getString(R.string.martesAbreviado)
        val miercolesAbreviado = context.getString(R.string.miercolesAbreviado)
        val juevesAbreviado = context.getString(R.string.juevesAbreviado)
        val viernesAbreviado = context.getString(R.string.viernesAbreviado)
        val sabadoAbreviado = context.getString(R.string.sabadoAbreviado)

        val dayAbbreviations = mapOf(
            DayOfWeek.SUNDAY to domingoAbreviado,
            DayOfWeek.MONDAY to lunesAbreviado,
            DayOfWeek.TUESDAY to martesAbreviado,
            DayOfWeek.WEDNESDAY to miercolesAbreviado,
            DayOfWeek.THURSDAY to juevesAbreviado,
            DayOfWeek.FRIDAY to viernesAbreviado,
            DayOfWeek.SATURDAY to sabadoAbreviado
        )

        val daysOfWeekToDisplay = DayOfWeek.values()

        val summary = daysOfWeekToDisplay.map { dayEnum ->
            val emotionsForDay = currentWeekEntries
                .filter {it.first.dayOfWeek == dayEnum}
                .mapNotNull { allEmotionsMap[it.second.emotionId] }

            val mostFrequent = if (emotionsForDay.isNotEmpty()) {
                emotionsForDay.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
            } else {
                null
            }

            DailyEmotionSummary(
                dayOfWeek = dayEnum,
                dayAbbreviation = dayAbbreviations[dayEnum] ?: "",
                mostFrequentEmotion = mostFrequent
            )
        }
        _weeklySummary.value = summary
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processMonthlySummary(entries: List<Pair<LocalDateTime, EmotionEntry>>, today: LocalDate) {
        val currentMonth = YearMonth.from(today)
        val monthlyEntries = entries.filter { YearMonth.from(it.first) == currentMonth }

        val summaryPoints = mutableListOf<MonthlyEmotionDataPoint>()

        (1..currentMonth.lengthOfMonth()).forEach { dayOfMonth ->
            val emotionsForDay = monthlyEntries
                .filter { it.first.dayOfMonth == dayOfMonth }
                .mapNotNull { allEmotionsMap[it.second.emotionId]?.numericalValue }

            if (emotionsForDay.isNotEmpty()) {
                summaryPoints.add(MonthlyEmotionDataPoint(dayOfMonth, emotionsForDay.average().toFloat()))
            }
            // Podrías añadir puntos con valor 0 o un valor base si no hay datos,
            // para que el gráfico no tenga saltos, si lo deseas.
        }
        _monthlySummary.value = summaryPoints
    }
}