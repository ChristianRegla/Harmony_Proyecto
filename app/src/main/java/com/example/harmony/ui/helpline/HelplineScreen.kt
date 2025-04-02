package com.example.harmony.ui.helpline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark
import com.example.harmony.ui.theme.Bluephone
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelplineScreen(navController: NavHostController, helplineViewModel: HelplineViewModel) {
    val context = LocalContext.current
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.header_linea_ayuda)
    val relajacion = context.getString(R.string.relajacion)

    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val inicio = ("Inicio")
    val backgroundColor = Color(0xFF1D1B20)

    // Contenedor del Drawer (menú lateral, lo vuelvo a especificar por si acaso)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContent(navController = navController, helplineViewModel = helplineViewModel)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)) {

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
                            label = { Text(inicio, color = Color.White) },
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
                HelpScreen()
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
fun DrawerContent(navController: NavHostController, helplineViewModel: HelplineViewModel) {
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(32.dp))

    Box(
        modifier = Modifier.fillMaxWidth(), // Asegura que el Box ocupe todo el ancho disponible
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_harmony),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }

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
        onClick = { navController.navigate("perfil") }
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
                text = context.getString(R.string.centro_de_ayuda),
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
        onClick = { helplineViewModel.cerrarSesion(navController) }
    )
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

@Composable
fun HelpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.estas_en_crisis),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id=R.color.blue_medium),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.comun_cate_con_amigos),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.recuerda_no_estas_solo),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        HelpCard(
            imageRes = R.drawable.drawable_image_1,
            title = stringResource(id = R.string.linea_de_la_vida),
            phone = stringResource(id = R.string._800_911_2000),
            description = stringResource(id = R.string.cobertura_nacional_disponible_24_7)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HelpCard(
            imageRes = R.drawable.drawable_image_1,
            title = stringResource(id = R.string.linea_de_la_vida),
            phone = stringResource(id = R.string._075),
            description = stringResource(id = R.string.cobertura_en_jalisco_disponible_24_7)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HelpCard(
            imageRes = R.drawable.drawable_image_3,
            title = stringResource(id = R.string.emergencias),
            phone = stringResource(id = R.string._911),
            description = stringResource(id = R.string.cobertura_nacional_disponible_24_7)
        )
    }
}

@Composable
fun HelpCard(imageRes: Int, title: String, phone: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(45.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = phone, fontSize = 20.sp, color = Bluephone)
            Text(text = description, fontSize = 11.sp, color = Color.Gray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HelplinePreview() {
    val navController = rememberNavController()
    HelplineScreen(navController = navController, helplineViewModel = HelplineViewModel())
}
