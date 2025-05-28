import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Home
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
import com.example.harmony.ui.registeremotions.RegisterEmotionsModel
import com.example.harmony.ui.registeremotions.RegisterEmotionsViewModel
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import java.util.Locale

// DATA CLASE PARA ACTIVIDADES
data class Activity(
    @DrawableRes val imageResId: Int,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RegisterEmotionsScreen(navController: NavHostController, registerEmotionsViewModel: RegisterEmotionsViewModel) {
    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    val currentCalendar = Calendar.getInstance()
    currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
    currentCalendar.set(Calendar.MINUTE, 0)
    currentCalendar.set(Calendar.SECOND, 0)
    currentCalendar.set(Calendar.MILLISECOND, 0)

// Sumar un día solo para comparación
    currentCalendar.add(Calendar.DAY_OF_MONTH, 1)


    val currentDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
        currentCalendar.get(Calendar.YEAR),
        currentCalendar.get(Calendar.MONTH) + 1,
        currentCalendar.get(Calendar.DAY_OF_MONTH)
    )

    val currentTime = String.format(Locale.getDefault(), "%02d:%02d",
        currentCalendar.get(Calendar.HOUR_OF_DAY),
        currentCalendar.get(Calendar.MINUTE)
    )

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

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
                            horizontal = screenWidth * 0.07f,
                            vertical = screenHeight * 0.03f
                        )
                ) {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = screenHeight*0.03f
                            ),
                        contentAlignment = Alignment.Center
                    ){
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
                                    contentDescription = stringResource(R.string.content_description_DatePicker)
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
                                    contentDescription = stringResource(R.string.content_description_TimePicker),
                                    modifier = Modifier.padding(start = 4.dp)
                                )
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

                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = screenHeight*0.03f
                            ),
                        contentAlignment = Alignment.Center
                    ){
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
                            verticalArrangement = Arrangement.spacedBy(screenHeight*0.02f),
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
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Button(
                            onClick = {
                                if (selectedEmotionIndex != -1 && selectedActivity != null &&
                                    selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {

                                    // Convertir fecha y hora seleccionadas a `Calendar`
                                    val selectedCalendar = Calendar.getInstance()
                                    val dateParts = selectedDate.split("-")
                                    val timeParts = selectedTime.split(":")

                                    selectedCalendar.set(Calendar.YEAR, dateParts[0].toInt())
                                    selectedCalendar.set(Calendar.MONTH, dateParts[1].toInt() - 1) // Meses van de 0 a 11
                                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dateParts[2].toInt())
                                    selectedCalendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                                    selectedCalendar.set(Calendar.MINUTE, timeParts[1].toInt())

                                    // Comparación con un día extra en la fecha actual
                                    if (selectedCalendar.after(currentCalendar)) {
                                        Toast.makeText(context, "Error: No puedes seleccionar una fecha futura.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        sendEmotions(selectedEmotionIndex, activities.indexOf(selectedActivity), selectedDate, selectedTime)
                                        Toast.makeText(context, "¡Registro guardado!", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Error: Selecciona emoción, actividad, fecha y hora.", Toast.LENGTH_SHORT).show()
                                }
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
                    }
                    Spacer(modifier = Modifier.height(16.dp))
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

                            if (selectedCalendar.after(currentCalendar)) {
                                Toast.makeText(context, "No puedes seleccionar una fecha futura.", Toast.LENGTH_SHORT).show()
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
                            val currentCalendar = Calendar.getInstance()
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedCalendar.set(Calendar.MINUTE, minute)

                            if (selectedCalendar.after(currentCalendar)) {
                                Toast.makeText(context, "No puedes seleccionar una hora futura.", Toast.LENGTH_SHORT).show()
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
                .size(70.dp)
                .clip(CircleShape)
                .border(6.dp, borderColor, CircleShape)
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,            // <— aquí anulamos el ripple
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