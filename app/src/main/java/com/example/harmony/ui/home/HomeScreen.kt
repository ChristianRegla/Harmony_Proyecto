package com.example.harmony.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
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

    // Contenedor del Drawer (menú lateral, lo vuelvo a especificar por si acaso)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContent(navController = navController, homeViewModel = homeViewModel)
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
                        modifier = Modifier.size(56.dp)
                    )
                },
                // Barra de abajo
                bottomBar = {
                    NavigationBar(containerColor = BlueDark) {
                        NavigationBarItem(
                            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
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
                            icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Relaxing", tint = Color.White, modifier = Modifier.alpha(0.5f)) },
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
            ) { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
                // ChatbotSection()
            }
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color.Transparent)
    )

}

@Composable
fun DrawerContent(navController: NavHostController, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val apodo = homeViewModel.apodo.collectAsState().value

    Spacer(modifier = Modifier.height(32.dp))

    Box(
        modifier = Modifier.fillMaxWidth(), // Asegura que el Box ocupe todo el ancho disponible
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Image(
            painter = painterResource(id = R.drawable.foto_avatar),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }

    Text(
        text = apodo,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )

    HorizontalDivider()

    Column(
        modifier = Modifier
            .background(color = Color(0xFFFAFAFA))
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Editar Perfil
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.3f)
                )
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.PersonOutline,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.editar_perfil),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = { navController.navigate("perfil") },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Notificaciones
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.NotificationsNone,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.notificaciones),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = {},
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Donaciones
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.MonetizationOn,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.donaciones),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = {},
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Centro de Ayuda
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.centro_de_ayuda),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                },
                selected = false,
                onClick = {},
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Cerrar Sesión
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Logout,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.cerrar_sesi_n),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = { homeViewModel.cerrarSesion(navController) },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
fun ChatbotSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_harmony),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.image_arrow_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_harmony),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = "¿Te gustaría contar algo?", fontSize = 16.sp, color = Color.Black)
            Text(text = "Estoy para lo que necesites", fontSize = 14.sp, color = Color(0xFF1D1B20))
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