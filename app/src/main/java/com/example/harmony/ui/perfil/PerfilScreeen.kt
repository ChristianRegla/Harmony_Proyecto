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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
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
import androidx. compose. ui. geometry. CornerRadius
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.harmony.ui.home.DrawerContent
import androidx. compose. ui. text. font. Font
import com.example.harmony.ui.theme.Magenta

/**
 * Created by codia-figma
 */
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
// Empty-47:1240-Rectangle 52 (Reutilizado para todos los items)
        Box(
            modifier = Modifier
                .advancedShadow(
                    color = Color(0x3f000000),
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        }
    ) {
// Box-47:1228-Perfil
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo_perfil),
                contentDescription = "Fondo de pantalla",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    header,
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 32.sp,
                                        fontFamily = FontFamily(Font(R.font.lobster)),
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueDark),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu_lateral),
                                    contentDescription = "Menú",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate("chat") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ico_mensaje), // Cargar ícono personalizado
                                    contentDescription = "Chat",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    )
                },
                bottomBar = {
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
                            selected = true,
                            onClick = {},
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
                            selected = false,
                            onClick = { navController.navigate("perfil") }
                        )
                    }
                },
                containerColor = Color.Transparent, // haz el scaffold transparente
                contentColor = Color.White // Ajusta el color del contenido si es necesario
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo1),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(412.dp, 272.dp),
                )
            }

// Box-47:1230-perfilImagen
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 69.dp, y = 180.dp)
                    .size(274.dp, 184.dp),
            ) {
// Image-47:1231-Avatar
                Image(
                    painter = painterResource(id = R.drawable.foto_avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 74.dp, y = 0.dp)
                        .size(127.dp, 130.dp),
                )
// Text-47:1236-Slappy
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentHeight()
                        .offset(x = 40.dp, y = 135.dp)
                        .width(194.dp),
                    text = usuario,
                    color = Color(0xffffffff),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
// Text-47:1237-youremail@domain.com
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentHeight()
                        .offset(x = 0.dp, y = 164.dp)
                        .width(274.dp),
                    text = "youremail@domain.com",
                    color = Color(0xfffafafa),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
            }
// Box-47:1238-perfilInformacion
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 35.dp, y = 404.dp)
                    .width(342.dp)
            ) {
// Grupo de 1
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color=Color(0x00000000))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Column {
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_editar_perfil, "Editar informacion de perfil"))
                        ProfileMenuItem(item = MenuItem(R.drawable.icononotificaciones, "Notificaciones", "Si"))
                        ProfileMenuItem(item = MenuItem(R.drawable.icono_idioma, "Idioma", "Español"))

                    }
                }
                Spacer(modifier = Modifier.height(14.dp))

// Grupo de 2
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color=Color(0x00000000))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Column {
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_seguridad, "Seguridad"))
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_tema, "Tema", "Modo Claro"))

                    }
                }
                Spacer(modifier = Modifier.height(14.dp))

// Grupo de 3
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color=Color(0x00000000))
                    .clip(RoundedCornerShape(8.dp))
                ) {
                    Column {
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_asistecia, "Ayuda y Soporte"))
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_contactanos, "Contactanos"))
                        ProfileMenuItem(item = MenuItem(R.drawable.ico_politicas, "Politica de Privacidad "))
                    }
                }
            }
        }
    }
}
@Composable
fun DrawerContent(navController: NavHostController) {
// Opciones del menú lateral
    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(
            onClick = { navController.navigate("profile") },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp) // Este es el espacio interno
        ) {
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", modifier = Modifier.padding(end = 8.dp))
            Text(
                text = "Perfil",
                color = Color(0xFF295E84),
                style = MaterialTheme.typography.bodyLarge
            )
        }
// Espacio entre las opciones
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate("settings") },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp) // Este es el espacio interno

        ) {
            Text(
                text = "Configuración",
                color = Color(0xFF295E84),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
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