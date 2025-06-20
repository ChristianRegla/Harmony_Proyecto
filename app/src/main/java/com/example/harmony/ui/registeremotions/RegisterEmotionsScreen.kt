package com.example.harmony.ui.registeremotions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.with
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.scale
import androidx.compose.runtime.LaunchedEffect
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.harmony.ui.components.Background_Register_Emotions
import java.util.Calendar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import sendEmotions
import java.util.Locale

// DATA CLASE PARA ACTIVIDADES
data class Activity(
    @DrawableRes val imageResId: Int,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun RegisterEmotionsScreen(navController: NavHostController, registerEmotionsViewModel: RegisterEmotionsViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    val currentCalendar = Calendar.getInstance()
    currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
    currentCalendar.set(Calendar.MINUTE, 0)
    currentCalendar.set(Calendar.SECOND, 0)
    currentCalendar.set(Calendar.MILLISECOND, 0)

    val now = Calendar.getInstance()

    var isSaved by remember { mutableStateOf(false) }

    val buttonWidth by animateDpAsState(
        targetValue = if (isSaved) screenWidth * 0.15f else screenWidth * 0.5f,
        animationSpec = tween(durationMillis = 600),
        label = "width"
    )

    val checkScale by animateFloatAsState(
        targetValue = if (isSaved) 2.3f else 1f, // Ajuste de escala más controlado
        animationSpec = tween(durationMillis = 600, easing = EaseInOut),
        label = "checkScale"
    )

    val todayEnd = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }

    var selectedDate by remember {
        mutableStateOf(
            String.format(Locale.getDefault(), "%04d-%02d-%02d",
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1,
                now.get(Calendar.DAY_OF_MONTH)
            )
        )
    }

    var selectedTime by remember {
        mutableStateOf(
            String.format(Locale.getDefault(), "%02d:%02d",
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE)
            )
        )
    }

    val headerTitle = ""
    val relajacion = context.getString(R.string.relajacion)
    val inicio = context.getString(R.string.inicio)

    val literataFamily = FontFamily(
        Font(R.font.literata_regular),
        Font(R.font.literata_bold)
    )

    // Estados para los diálogos de fecha y hora
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val hoy = stringResource(R.string.hoy)
    var dateState by remember {
        mutableStateOf(
            hoy+", ${calendar.get(Calendar.DAY_OF_MONTH)} ${getMonthName(calendar.get(Calendar.MONTH))}"
        )
    }
    var timeState by remember {
        mutableStateOf(
            String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
            )
        )
    }

    // Lista de cinco emojis (como cadenas) y el estado del seleccionado
    val emotionIcons = listOf<Int>(
        R.drawable.ic_emotion_happy,
        R.drawable.ic_emotion_sad,
        R.drawable.ic_emotion_angry,
        R.drawable.ic_emotion_love,
        R.drawable.ic_emotion_idkbutnothappy
    )

    //Cambiar por strings
    val feliz=stringResource(R.string.ED_feliz)
    val triste=stringResource(R.string.ED_triste)
    val enojado=stringResource(R.string.ED_enojado)
    val amor=stringResource(R.string.ED_amor)
    val sorprendido=stringResource(R.string.ED_sorprendido)
    val emotionDescriptions = listOf(
        feliz, triste, enojado, amor, sorprendido
    )

    var selectedEmotionIndex by remember { mutableIntStateOf(-1) }

    // Lista de actividades con ícono y nombre
    val trabajo = stringResource(R.string.A_trabajo)
    val amigos = stringResource(R.string.A_amigos)
    val familia = stringResource(R.string.A_familia)
    val escuela = stringResource(R.string.A_escuela)
    val pareja = stringResource(R.string.A_pareja)
    val hobbies = stringResource(R.string.A_hobbies)
    val salud = stringResource(R.string.A_salud)
    val otro = stringResource(R.string.A_otro)

    val activities = listOf(
        Activity(R.drawable.ic_work, trabajo),
        Activity(R.drawable.ic_friends, amigos),
        Activity(R.drawable.ic_family, familia),
        Activity(R.drawable.ic_school, escuela),
        Activity(R.drawable.ic_love, pareja),
        Activity(R.drawable.ic_hobby, hobbies),
        Activity(R.drawable.ic_health, salud),
        Activity(R.drawable.ic_other, otro)
    )
    var selectedActivity by remember { mutableStateOf<Activity?>(null) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (isSaved) {
        LaunchedEffect(isSaved) {
            delay(1000) // espera breve para mostrar la animación del check
            // Limpiar los campos
            selectedEmotionIndex = -1
            selectedActivity = null

            val now = Calendar.getInstance()
            selectedDate = String.format("%04d-%02d-%02d",
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1,
                now.get(Calendar.DAY_OF_MONTH)
            )
            dateState = context.getString(R.string.hoy) + ", ${now.get(Calendar.DAY_OF_MONTH)} ${getMonthName(now.get(Calendar.MONTH))}"

            selectedTime = String.format("%02d:%02d",
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE)
            )
            timeState = selectedTime

            delay(2000) // tiempo restante para completar los 3s
            isSaved = false
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(navController = navController, drawerActions = registerEmotionsViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            // Barra de arriba
            topBar = {
                TopBar(
                    onOpenDrawer = {
                        scope.launch {
                            if(drawerState.isClosed) drawerState.open()
                        }
                    },
                    // Este es el título que va en medio de la barra superior
                    title = headerTitle,
                    navController = navController,
                    modifier = Modifier.wrapContentHeight()
                )
            },
            // Barra de abajo
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.height(80.dp),
                    containerColor = BlueDark
                ) {
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = R.drawable.home_unselected),
                            contentDescription = "Home",
                            tint = Color.White
                        ) },
                        label = { Text(inicio, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                        selected = true,
                        onClick = { navController.navigate("main") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(
                            painter = painterResource(id = R.drawable.relax_unselected),
                            contentDescription = "Relaxing",
                            tint = Color.White
                        ) },
                        label = { Text(relajacion, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                        selected = false,
                        onClick = { navController.navigate("relax") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent
                        )
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = Color.White

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Background_Register_Emotions(modifier = Modifier.matchParentSize())

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = screenWidth * 0.07f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = screenHeight * 0.03f
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.pregunta_como_se_siente),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontFamily = literataFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(end = 3.dp)
                                )
                                Text(
                                    text = dateState,
                                    style = TextStyle(
                                        textDecoration = TextDecoration.Underline
                                    )
                                )
                                Box(
                                    modifier = Modifier.size(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = stringResource(R.string.content_description_DatePicker),
                                        modifier = Modifier.fillMaxSize()
                                            .requiredSize(24.dp)
                                    )
                                }
                            }

                        }

                        OutlinedButton(
                            onClick = { showTimePicker = true },
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ){
                            Icon(
                                imageVector = Icons.Filled.AccessTime,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(end = 3.dp)
                            )
                            Text(
                                text = timeState,
                                style = TextStyle(
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = stringResource(R.string.content_description_TimePicker),
                                    modifier = Modifier.fillMaxSize()
                                        .padding(start = 10.dp)
                                        .requiredSize(24.dp)
                                )
                            }
                                }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // CONTENEDOR DE LOS BOTONES DE EMOJIS
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            emotionIcons.forEachIndexed { index, iconResId ->
                                IconButton(
                                    onClick = {
                                        selectedEmotionIndex = index
                                    },
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (selectedEmotionIndex == index) Color(0xFF388BAC) else Color.Transparent
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = iconResId),
                                        contentDescription = emotionDescriptions[index],
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = screenHeight * 0.03f
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.selecciona_una_actividad),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontFamily = literataFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    //CONTENEDOR DE LAS ACTIVIDADES
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0070A8), shape = RoundedCornerShape(10.dp))
                            .padding(
                                horizontal = screenWidth * 0.04f,
                                vertical = screenHeight * 0.02f
                            )
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.025f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            activities.forEach { activity ->
                                ActivityButton(
                                    activity = activity,
                                    isSelected = (activity == selectedActivity)
                                ) { selectedActivity = activity }
                            }
                        }
                    }

                    //boton ok
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Button(
                            onClick = {
                                when {
                                    selectedEmotionIndex == -1 -> {
                                        Toast.makeText(
                                            context,
                                            R.string.ME_Emociones,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    selectedActivity == null -> {
                                        Toast.makeText(
                                            context,
                                            R.string.ME_Actividades,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    selectedDate.isEmpty() -> {
                                        Toast.makeText(
                                            context,
                                            R.string.ME_Date,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    selectedTime.isEmpty() -> {
                                        Toast.makeText(
                                            context,
                                            R.string.ME_Time,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {
                                        val selectedCalendar = Calendar.getInstance()
                                        val dateParts = selectedDate.split("-")
                                        val timeParts = selectedTime.split(":")

                                        selectedCalendar.set(Calendar.YEAR, dateParts[0].toInt())
                                        selectedCalendar.set(
                                            Calendar.MONTH,
                                            dateParts[1].toInt() - 1
                                        )
                                        selectedCalendar.set(
                                            Calendar.DAY_OF_MONTH,
                                            dateParts[2].toInt()
                                        )
                                        selectedCalendar.set(
                                            Calendar.HOUR_OF_DAY,
                                            timeParts[0].toInt()
                                        )
                                        selectedCalendar.set(Calendar.MINUTE, timeParts[1].toInt())
                                        selectedCalendar.set(Calendar.SECOND, 0)
                                        selectedCalendar.set(Calendar.MILLISECOND, 0)

                                        if (selectedCalendar.after(now)) {
                                            Toast.makeText(
                                                context,
                                                R.string.ME_Fecha_Hora_Futura,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            sendEmotions(
                                                selectedEmotionIndex,
                                                activities.indexOf(selectedActivity),
                                                selectedDate,
                                                selectedTime
                                            )
                                            isSaved = true
                                        }
                                    }
                                }
                            },
                            enabled = !isSaved, // ← desactiva durante la animación
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF0080),
                                contentColor = Color.Black,
                                disabledContainerColor = Color(0xFFFF0080), // mismo color para que no se vea "apagado"
                                disabledContentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .width(buttonWidth)
                                .fillMaxHeight(0.35f)
                        ) {
                            AnimatedContent(
                                targetState = isSaved,
                                transitionSpec = {
                                    (fadeIn(tween(300)) + scaleIn(tween(300))) with fadeOut(tween(100))
                                },
                                label = "button-content"
                            ) { saved ->
                                Box(
                                    modifier = Modifier.size(25.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (saved) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "Guardado",
                                            tint = Color.Black,
                                            modifier = Modifier.scale(checkScale) // Sincronizar escala con botón
                                        )
                                    } else {
                                        Text("OK", color = Color.White)
                                    }
                                }
                            }
                        }

                    }
                }
            }

                // DIÁLOGOS DE FECHA Y HORA
                if (showDatePicker) {
                    val datePicker = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(year, month, dayOfMonth)

                            val currentCalendar = Calendar.getInstance()
                            currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
                            currentCalendar.set(Calendar.MINUTE, 0)
                            currentCalendar.set(Calendar.SECOND, 0)
                            currentCalendar.set(Calendar.MILLISECOND, 0) // Asegurar que comparamos correctamente

                            if (selectedCalendar.after(todayEnd)) {
                                Toast.makeText(context, R.string.ME_Fecha_Futura, Toast.LENGTH_SHORT).show()
                            } else {
                                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                dateState = "$dayOfMonth de ${getMonthName(month)}"
                            }

                            showDatePicker = false
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )

                    datePicker.setOnCancelListener {
                        showDatePicker = false
                    }

                    datePicker.show()
                }
                if (showTimePicker) {
                    val timePicker = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            val selectedCalendar = Calendar.getInstance()

                            val dateParts = selectedDate.split("-")
                            selectedCalendar.set(Calendar.YEAR, dateParts[0].toInt())
                            selectedCalendar.set(Calendar.MONTH, dateParts[1].toInt() - 1)
                            selectedCalendar.set(Calendar.DAY_OF_MONTH, dateParts[2].toInt())
                            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedCalendar.set(Calendar.MINUTE, minute)
                            selectedCalendar.set(Calendar.SECOND, 0)
                            selectedCalendar.set(Calendar.MILLISECOND, 0)

                            if (selectedCalendar.after(now)) {
                                Toast.makeText(context, R.string.ME_Hora_Futura, Toast.LENGTH_SHORT).show()
                            } else {
                                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                                timeState = selectedTime
                            }

                            showTimePicker = false
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )

                    timePicker.setOnCancelListener {
                        showTimePicker = false
                    }

                    timePicker.show()
                }
            }
        }
    }


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActivityButton(
    activity: Activity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = Color(0xFF388BAC)
    val backgroundColor = if (isSelected) borderColor else Color.White
    val iconTint = if (isSelected) Color.White else Color.Black

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .border(6.dp, borderColor, CircleShape)
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = activity.imageResId),
                contentDescription = activity.label,
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(iconTint)
            )
        }
        Text(
            text = activity.label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getMonthName(month: Int): String {
    return when (month) {
        0 -> "enero"
        1 -> "febrero"
        2 -> "marzo"
        3 -> "abril"
        4 -> "mayo"
        5 -> "junio"
        6 -> "julio"
        7 -> "agosto"
        8 -> "septiembre"
        9 -> "octubre"
        10 -> "noviembre"
        11 -> "diciembre"
        else -> "??"
    }
}

@Preview(showBackground = true)
@Composable
fun MoodAndActivityScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    RegisterEmotionsScreen(
        navController = navController,
        registerEmotionsViewModel = RegisterEmotionsViewModel(
            RegisterEmotionsModel(context),
            context
        )
    )
}