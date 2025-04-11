import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.harmony.ui.components.Background_Register_Emotions
import java.util.Calendar
import androidx.compose.ui.text.TextStyle

// DATA CLASE PARA ACTIVIDADES
data class Activity(val icon: ImageVector, val label: String)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MoodAndActivityScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Estados para los diálogos de fecha y hora
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    var dateState by remember {
        mutableStateOf(
            "Hoy, ${calendar.get(Calendar.DAY_OF_MONTH)} de ${getMonthName(calendar.get(Calendar.MONTH))}"
        )
    }
    var timeState by remember { mutableStateOf(
        "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
    ) }

    // Lista de cinco emojis (como cadenas) y el estado del seleccionado
    val emojis = listOf("😍", "😊", "😐", "😔", "😡")
    var selectedEmoji by remember { mutableStateOf(emojis[0]) }

    // Lista de actividades con ícono y nombre
    val activities = listOf(
        Activity(Icons.Filled.Work, "Trabajo"),
        Activity(Icons.Filled.People, "Amigos"),
        Activity(Icons.Filled.FamilyRestroom, "Familia"),
        Activity(Icons.Filled.School, "Escuela"),
        Activity(Icons.Filled.Favorite, "Pareja"),
        Activity(Icons.Filled.SportsEsports, "Hobbie"),
        Activity(Icons.Filled.HealthAndSafety, "Salud"),
        Activity(Icons.Filled.MoreHoriz, "Otro")
    )
    var selectedActivity by remember { mutableStateOf<Activity?>(null) }

    Scaffold(
        topBar = {
            TopBarClose(
                title = "¿Cómo te sientes?",
                onClose = { /* Acción para cerrar o retroceder */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
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
                            horizontal = screenWidth * 0.07f,
                            vertical = screenHeight * 0.03f
                        )
                ) {
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
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = (-screenWidth * 0f))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.CalendarMonth,
                                                contentDescription = ""
                                            )
                                            Text(
                                                text = dateState,
                                                style = TextStyle(
                                                    textDecoration = TextDecoration.Underline
                                                )
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ArrowDropDown,
                                                contentDescription = "Abrir selector de fecha"
                                            )
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.AccessTime,
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = timeState,
                                            style = TextStyle(
                                                textDecoration = TextDecoration.Underline
                                            )
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            contentDescription = "Abrir selector de hora",
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                    }
                                }
                            }
                    Spacer(modifier = Modifier.height(16.dp))

                    // CONTENEDOR BLANCO PARA LOS BOTONES DE EMOJIS
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            emojis.forEach { emoji ->
                                EmojiTextButton(
                                    emoji = emoji,
                                    isSelected = (emoji == selectedEmoji)
                                ) { selectedEmoji = emoji }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // CONTENEDOR AZUL PARA LOS BOTONES DE ACTIVIDADES
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0070A8), shape = RoundedCornerShape(16.dp))
                            .padding(
                                horizontal = screenWidth * 0.08f
                            )
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    Spacer(modifier = Modifier.weight(1f))

                    // BOTÓN INFERIOR (ROSA) PARA GUARDAR/CONTINUAR
                    Button(
                        onClick = {
                            // Aquí procesas dateState, timeState, selectedEmoji y selectedActivity
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF0080),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .padding(
                                horizontal = screenWidth * 0.1f
                            )
                            .fillMaxWidth(0.5f)
                    ) {
                        Text("OK")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // DIÁLOGOS DE FECHA Y HORA
                if (showDatePicker) {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            dateState = "$dayOfMonth de ${getMonthName(month)}"
                            showDatePicker = false
                            showTimePicker = true
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                if (showTimePicker) {
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)
                            timeState = String.format("%02d:%02d", hourOfDay, minute)
                            showTimePicker = false
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            }
        }
    )
}

@Composable
fun EmojiTextButton(emoji: String, isSelected: Boolean, onClick: () -> Unit) {
    // Botón para el emoji usando medidas fijas relativas (50dp)
    val backgroundColor = if (isSelected) Color(0xFFffd54f) else Color.Transparent
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 24.sp)
    }
}

@Composable
fun ActivityButton(activity: Activity, isSelected: Boolean, onClick: () -> Unit) {
    // Botón de actividad con ícono y texto en un recuadro
    val borderColor = if (isSelected) Color.White else Color.LightGray
    val backgroundColor = if (isSelected) Color(0xFFB3E5FC) else Color.Transparent
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = activity.icon,
            contentDescription = activity.label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = activity.label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            textAlign = TextAlign.Center
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClose(title: String, onClose: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0070A8))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoodAndActivityScreenPreview() {
    MoodAndActivityScreen()
}
