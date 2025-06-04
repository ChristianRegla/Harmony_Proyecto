package com.example.harmony.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.header_menu_principal)
    val relajacion = context.getString(R.string.relajacion)



    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.loadEmotionSummaries()
    }


    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )


    // Contenedor del Drawer (menú lateral, lo vuelvo a especificar por si acaso)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(navController = navController, drawerActions = homeViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_inicio),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

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
                            icon = { Icon(painterResource(id = R.drawable.home_selected), contentDescription = "Home", tint = Color.White) },
                            label = { Text(headerTitle, color = Color.White) },
                            selected = true,
                            onClick = {},
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
            ) { innerpadding ->
                ScreenContent(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding),
                    navController = navController,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

@Composable
fun ScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    // Cargamos los estados del ViewModel
    val weeklySummary by homeViewModel.weeklySummary.collectAsState()
    val monthlySummary by homeViewModel.monthlySummary.collectAsState()
    val isLoadingSummaries by homeViewModel.isLoadingSummaries.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        val tituloChatbot = context.getString(R.string.te_gustar_a_contar_algo)
        val subtituloChatbot = context.getString(R.string.estoy_para_lo_que_necesites)
        ChatbotSection(
            titulo = tituloChatbot,
            subtitulo = subtituloChatbot,
            imageResId = R.drawable.logo_harmony,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            navController = navController,
            onClick = { navController.navigate("chatbot") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoadingSummaries) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            if (weeklySummary.isNotEmpty()) {
                WeeklyEmotionSummaryView(summary = weeklySummary)
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f),
                        contentColor = Color.White.copy(alpha = 0.8f)
                    )
                ) {
                    val mensajeSinDatos = context.getString(R.string.no_hay_datos_para_el_resumen_semanal)
                    Text(
                        mensajeSinDatos,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (monthlySummary.isNotEmpty()) {
                MonthlyEmotionChartView(summaryPoints = monthlySummary)
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f),
                        contentColor = Color.White.copy(alpha = 0.8f)
                    )
                ) {
                    val mensajeSinDatos = context.getString(R.string.no_hay_datos_para_el_resumen_mensual)
                    Text(
                        mensajeSinDatos,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        val tituloEmociones = context.getString(R.string.como_estuvo_el_dia)
        val subtituloEmociones = context.getString(R.string.lleva_un_registro)
        ChatbotSection(
            titulo = tituloEmociones,
            subtitulo = subtituloEmociones,
            imageResId = R.drawable.guarda_tu_emocion,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            navController = navController,
            onClick = { navController.navigate("registerEmotions") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val tituloLineaDeAyuda = context.getString(R.string.linea_de_ayuda)
        val subtituloLineaDeAyuda = context.getString(R.string.disponible_24_7)
        ChatbotSection(
            titulo = tituloLineaDeAyuda,
            subtitulo = subtituloLineaDeAyuda,
            imageResId = R.drawable.ic_linea_de_ayuda,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            navController = navController,
            onClick = { navController.navigate("helpline") }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}



@Composable
fun ChatbotSection(
    titulo: String,
    subtitulo: String,
    imageResId: Int = 0,
    modifier: Modifier = Modifier,
    navController: NavController,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .height(80.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = titulo, fontSize = 16.sp, color = Color.Black)
                Text(text = subtitulo, fontSize = 14.sp, color = Color(0xFF1D1B20))
            }

            Spacer(modifier = Modifier.weight(1f))

            Image( // Flecha a la derecha
                painter = painterResource(id = R.drawable.image_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun WeeklyEmotionSummaryView(summary: List<DailyEmotionSummary>) {
    val context = LocalContext.current
    Column {
        val tituloResumen = context.getString(R.string.resumen_semanal)
        Text(
            tituloResumen,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f),
                contentColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            if (summary.isEmpty()) {
                Text(
                    "No hay datos semanales.",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(items = summary, key = { it.dayOfWeek.ordinal }) { daySummary ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 4.dp).width(IntrinsicSize.Min)
                        ) {
                            Text(
                                text = daySummary.dayAbbreviation,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        daySummary.mostFrequentEmotion?.color?.copy(alpha = 0.85f) ?: Color.LightGray.copy(alpha = 0.4f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (daySummary.mostFrequentEmotion != null) {
                                    Icon(
                                        painter = painterResource(id = daySummary.mostFrequentEmotion.iconRes),
                                        contentDescription = daySummary.mostFrequentEmotion.name,
                                        modifier = Modifier.size(28.dp),
                                        tint = if (daySummary.mostFrequentEmotion.color.luminance() > 0.5f) Color.Black.copy(alpha = 0.75f) else Color.White.copy(alpha = 0.9f)
                                    )
                                } else {
                                    Icon(
                                        // Por si está vacío de mientras dejo ese jeje
                                        painter = painterResource(id = R.drawable.ic_linea_de_ayuda),
                                        contentDescription = "Sin datos",
                                        modifier = Modifier.size(28.dp),
                                        tint = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }
                            Text(
                                text = daySummary.mostFrequentEmotion?.name ?: "-",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 6.dp).heightIn(min = 28.dp),
                                color = Color.White.copy(alpha = 0.8f),
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MonthlyEmotionChartView(summaryPoints: List<MonthlyEmotionDataPoint>) {
    val context = LocalContext.current
    Column {
        val resumenMensual = context.getString(R.string.resumen_mensual)
        Text(
            resumenMensual,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White // Asumiendo fondo oscuro
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp), // Un poco más de altura para los gráficos de YCharts a veces viene bien
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f) // Fondo semi-transparente
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            if (summaryPoints.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No hay datos para el gráfico mensual.",
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                // 1. Transforma tus datos a `Point` de YCharts
                val pointsData: List<Point> = summaryPoints.map {
                    Point(x = it.dayOfMonth.toFloat(), y = it.averageEmotionValue)
                }

                // 2. Configura los ejes
                val maxDays = summaryPoints.maxOfOrNull { it.dayOfMonth }?.toFloat() ?: 31f
                val stepsXAxis = summaryPoints.distinctBy { it.dayOfMonth }.count().coerceAtLeast(1)

                val xAxisData = AxisData.Builder()
                    .axisStepSize(30.dp)
                    .backgroundColor(Color.Transparent)
                    .steps(pointsData.size.coerceAtLeast(1) -1)
                    .labelData { index ->
                        val pointIndex = (index * (pointsData.size - 1) / (stepsXAxis -1).coerceAtLeast(1) ).coerceIn(0, pointsData.size -1)
                        pointsData.getOrNull(pointIndex)?.x?.toInt()?.toString() ?: ""
                    }
                    .axisLineColor(Color.White.copy(alpha = 0.5f))
                    .build()

                val minYValue = pointsData.minOfOrNull { it.y } ?: 0f
                val maxYValue = pointsData.maxOfOrNull { it.y } ?: 5f

                val yAxisData = AxisData.Builder()
                    .steps(((maxYValue - minYValue).coerceAtLeast(1f)).toInt().coerceAtLeast(1))
                    .backgroundColor(Color.Transparent)
                    .labelData { value -> String.format("%.1f", value * (maxYValue-minYValue)/((maxYValue - minYValue).coerceAtLeast(1f)).toInt().coerceAtLeast(1) + minYValue) }
                    .axisLineColor(Color.White.copy(alpha = 0.5f))
                    .build()

                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsData,
                                lineStyle = LineStyle(
                                    color = MaterialTheme.colorScheme.secondary,
                                    width = 3f
                                ),
                                intersectionPoint = IntersectionPoint(
                                    color = MaterialTheme.colorScheme.secondary,
                                    radius = 4.dp
                                ),
                                selectionHighlightPoint = SelectionHighlightPoint(
                                    color = MaterialTheme.colorScheme.tertiary
                                ),
                                shadowUnderLine = ShadowUnderLine(
                                    alpha = 0.2f,
                                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.secondary,
                                            Color.Transparent
                                        )
                                    )
                                ),
                                selectionHighlightPopUp = SelectionHighlightPopUp(
                                    popUpLabel = { x, y ->
                                        val diaTraducido = context.getString(R.string.popup_dia_grafico)
                                        "$diaTraducido ${x.toInt()}: ${String.format("%.1f", y)}"
                                    },
                                    labelColor = Color.Black,
                                    labelSize = 12.sp,
                                    backgroundColor = Color.White.copy(alpha = 0.9f),
                                )
                            )
                        )
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(color = Color.White.copy(alpha = 0.2f)), // Líneas de la cuadrícula
                    backgroundColor = Color.Transparent, // Fondo del área del gráfico
                    paddingRight = 16.dp, // Añade padding si es necesario
                    bottomPadding = 10.dp // Espacio para las etiquetas del eje X
                )

                // 4. Muestra el LineChart
                Box(modifier = Modifier.padding(16.dp).fillMaxSize()) { // Padding dentro de la Card
                    LineChart(
                        modifier = Modifier.fillMaxSize(),
                        lineChartData = lineChartData
                    )
                }
            }
        }
    }
}

// Función de utilidad para obtener la luminancia de un color (para decidir tinte del ícono)
fun Color.luminance(): Float {
    val r = this.red
    val g = this.green
    val b = this.blue
    return (0.2126f * r + 0.7152f * g + 0.0722f * b)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    HomeScreen(navController = navController, homeViewModel = HomeViewModel(HomeModel(context), context))
}