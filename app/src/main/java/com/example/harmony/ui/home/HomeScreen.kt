package com.example.harmony.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
                DrawerContentComponent(navController = navController, drawerActions = homeViewModel, dataBaseActions = homeViewModel)
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
                                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
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
                                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
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
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ScreenContent(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxWidth().fillMaxHeight()) {
        Spacer(modifier = Modifier.height(32.dp))
        val tituloChatbot = context.getString(R.string.te_gustar_a_contar_algo)
        val subtituloChatbot = context.getString(R.string.estoy_para_lo_que_necesites)
        ChatbotSection(
            titulo = tituloChatbot,
            subtitulo = subtituloChatbot,
            imageResId = R.drawable.logo_harmony,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 16.dp, end = 16.dp),
            navController = navController,
            onClick = { navController.navigate("chatbot") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val tituloEmociones = context.getString(R.string.como_estuvo_el_dia)
        val subtituloEmociones = context.getString(R.string.lleva_un_registro)
        ChatbotSection(
            titulo = tituloEmociones,
            subtitulo = subtituloEmociones,
            imageResId = R.drawable.guarda_tu_emocion,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 16.dp, end = 16.dp),
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
                .height(80.dp)
                .padding(start = 16.dp, end = 16.dp),
            navController = navController,
            onClick = { navController.navigate("helpline") }
        )


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

            Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar la flecha a la derecha

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

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    HomeScreen(navController = navController, homeViewModel = HomeViewModel(HomeModel(context), context))
}