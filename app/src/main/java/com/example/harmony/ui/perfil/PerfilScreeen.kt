package com.example.harmony.ui.perfil

import androidx.compose.foundation.Image
import com.example.harmony.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx. compose. ui. draw. drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.harmony.ui.home.DrawerContent
import androidx. compose. ui. text. font. Font
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.TopBar


data class MenuItem(
    val iconId: Int,
    val text: String,
    val trailingText: String? = null,
    val onClick: () -> Unit = {}
)

@Composable
fun ProfileMenuItem(item: MenuItem) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .size(342.dp, 40.dp) // Altura estimada, se ajustará por el contenido
    ) {
        //(Reutilizado para todos los items)
        Box(
            modifier = Modifier
                .advancedShadow(
                    color = Color(0xFF000000),
                    alpha = 0.25f,
                    cornersRadius = 8.dp,
                    shadowBlurRadius = 4.dp,
                    offsetX = 0.dp,
                    offsetY = 1.dp
                )
                .background(Color(0xff295e84))
                .fillMaxWidth()
                .height(40.dp), // Altura fija para cada item
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(
                        text = item.text,
                        color = Color(0xffffffff),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(200.dp) // Ajustar ancho si es necesario
                    )
                }
                item.trailingText?.let {
                    Text(
                        text = it,
                        color = Color(0xffffffff),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Right,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(60.dp) // Ajustar ancho si es necesario
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController, perfilViewModel: PerfilViewModel) {
    val context = LocalContext.current
    val usuario = context.getString(R.string.user_name)
    val header = context.getString(R.string.header_perfil)
    val scrollState = rememberScrollState()
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
                DrawerContent(navController = navController, perfilViewModel = perfilViewModel)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (scaffold, containerInfoUsuario, fotoPerfil, nombreUsuario, correoUsuario, containerScrollView,
                containerOpciones, grupo1, grupo2, grupo3, topBar, bottomNavbar, fondoImg, fondoAzul
            ) = createRefs()

            // Fondo de pantalla
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(fondoImg){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo_perfil),
                    contentDescription = "Fondo de pantalla",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Image(
                painter = painterResource(id = R.drawable.fondo1),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(412.dp, 272.dp)
                    .padding(top = 0.dp)
                    .constrainAs(fondoAzul) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Scaffold(
                topBar = {
                    TopBar(
                        onOpenDrawer = {
                            scope.launch {
                                if(drawerState.isClosed) drawerState.open()
                            }
                        },
                        // Este es el título que va en medio de la barra superior
                        title = header,
                        modifier = Modifier.size(20.dp)
                    )
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = BlueDark,
                    ) {
                        NavigationBar(containerColor = BlueDark) {
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = "Home",
                                        tint = Color.White
                                    )
                                },
                                label = { Text("Home", color = Color.White) },
                                selected = false,
                                onClick = { navController.navigate("main") },
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Profile",
                                        tint = Color.White,
                                        modifier = Modifier.alpha(0.5f)
                                    )
                                },
                                label = {
                                    Text(
                                        "Profile",
                                        color = Color.White,
                                        modifier = Modifier.alpha(0.5f)
                                    )
                                },
                                selected = true,
                                onClick = { navController.navigate("perfil") }
                            )
                        }
                    }

                },
                containerColor = Color.Transparent, // haz el scaffold transparente
                contentColor = Color.White, // Ajusta el color del contenido si es necesario
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scaffold) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        // Foto de perfil
                        Image(
                            painter = painterResource(id = R.drawable.foto_avatar),
                            contentDescription = "Fondo de pantalla",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp, 120.dp)

                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        // Nombre del usuario
                        Text(
                            text = usuario,
                            color = Color(0xffffffff),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "youremail@domain.com",
                            color = Color(0xfffafafa),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                    }

                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column (
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_editar_perfil,
                                            "Editar informacion de perfil"
                                        )
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.icononotificaciones,
                                            "Notificaciones",
                                            "Si"
                                        )
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.icono_idioma,
                                            "Idioma",
                                            "Español"
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))

                            // Grupo de 2
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProfileMenuItem(item = MenuItem(R.drawable.ico_seguridad, "Seguridad"))
                                    ProfileMenuItem(item = MenuItem(R.drawable.ico_tema, "Tema", "Modo Claro"))
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            // Grupo de 3
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier.wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_asistecia,
                                            "Ayuda y Soporte"
                                        )
                                    )
                                    ProfileMenuItem(item = MenuItem(R.drawable.ico_contactanos, "Contactanos"))
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_politicas,
                                            "Politica de Privacidad "
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        // Box-47:1228-Perfil
        Box(modifier = Modifier.fillMaxSize()) {
// Box-47:1238-perfilInformacion
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 35.dp, y = 404.dp)
                    .width(342.dp)
            ) {
// Grupo de 1

            }
        }

    }
}
@Composable
fun DrawerContent(navController: NavHostController, perfilViewModel: PerfilViewModel) {
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
        onClick = { perfilViewModel.cerrarSesion(navController) }
    )
}
fun Modifier.advancedShadow(
    color: Color,
    alpha: Float,
    cornersRadius: Dp,
    shadowBlurRadius: Dp,
    offsetX: Dp,
    offsetY: Dp
): Modifier = this.then(
    Modifier.drawBehind {
        drawRoundRect(
            color = color.copy(alpha = alpha),
            size = size
        )
    }
)
@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    val navController = rememberNavController()
    PerfilScreen(navController = navController, perfilViewModel = PerfilViewModel())
}