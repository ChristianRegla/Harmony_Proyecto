package com.example.harmony.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    val headerTitle = context.getString(R.string.header_menu_principal)

    // Controlador del Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Contenedor del Drawer
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
                topBar = {
                    TopBar(
                        onOpenDrawer = {
                            scope.launch {
                                if(drawerState.isClosed) drawerState.open()
                            }
                        },
                        title = headerTitle,
                        modifier = Modifier.size(20.dp)
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = BlueDark) {
                        NavigationBarItem(
                            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
                            label = { Text("Home", color = Color.White) },
                            selected = true,
                            onClick = {},
                        )
                        NavigationBarItem(
                            icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", tint = Color.White, modifier = Modifier.alpha(0.5f)) },
                            label = { Text("Profile", color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = {}
                        )
                    }
                },
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
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
    Box() {

    }

    Spacer(modifier = Modifier.height(32.dp))

    Image(
        painter = painterResource(id = R.drawable.logo_harmony),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
    )

    Text(
        text = "Slappy",
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )

    HorizontalDivider()

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Editar Perfil
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.PersonOutline,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.editar_perfil),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Notificaciones
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.notificaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Donaciones
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.MonetizationOn,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.donaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Centro de Ayuda
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.notificaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Cerrar Sesión
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.cerrar_sesi_n),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = { homeViewModel.cerrarSesion(navController) }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController, homeViewModel = HomeViewModel())
}

@Composable
fun ChatbotSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.container_chatbot_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
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