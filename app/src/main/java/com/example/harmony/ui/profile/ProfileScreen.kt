package com.example.harmony.ui.profile

import androidx.compose.foundation.Image
import com.example.harmony.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

/**
 * Created by codia-figma
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val usuario = context.getString(R.string.user_name)
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
                                    "Perfil",
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
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 35.dp, y = 404.dp)
                    .size(342.dp, 376.dp),
            ) {
                // Box-47:1239-pI_seccion1
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(342.dp, 121.dp),
                ) {
                    // Empty-47:1240-Rectangle 52
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .advancedShadow(
                                color = Color(0x3f000000),
                                alpha = 0.25f,
                                cornersRadius = 8.dp,
                                shadowBlurRadius = 4.dp,
                                offsetX = 0.dp,
                                offsetY = 1.dp
                            )
                            .background(Color(0xff295e84), RoundedCornerShape(8.dp))
                            .size(342.dp, 121.dp),
                    )
                    // Box-47:1241-componenteEditarPerfil
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 14.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Text-I47:1241;58:896-Editar Informacion de perfil
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Editar informacion de perfil",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Image-47:1242-iconoEditarPerfil
                    Image(
                        painter = painterResource(id = R.drawable.ico_editar_perfil),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 20.dp, y = 15.dp)
                            .size(18.223.dp, 23.dp),
                    )
                    // Box-47:1243-componenteNotificaciones
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 49.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1243;58:895-iconoNotificaciones
                        Image(
                            painter = painterResource(id = R.drawable.icononotificaciones),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1243;58:896-Notificaciones
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Notificaciones",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                        // Text-I47:1243;58:897-Si
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 62.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Si",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Box-47:1244-componenteIdioma
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 86.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1244;58:895-iconoIdioma
                        Image(
                            painter = painterResource(id = R.drawable.icono_idioma),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1244;58:896-Idioma
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Idioma",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                        // Text-I47:1244;58:897-Espanol
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 62.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Español",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                // Box-47:1245-pI_seccion3
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 255.dp)
                        .size(342.dp, 121.dp),
                ) {
                    // Empty-47:1246-Rectangle 52
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .advancedShadow(
                                color = Color(0x3f000000),
                                alpha = 0.25f,
                                cornersRadius = 8.dp,
                                shadowBlurRadius = 4.dp,
                                offsetX = 0.dp,
                                offsetY = 1.dp
                            )
                            .background(Color(0xff295e84), RoundedCornerShape(8.dp))
                            .size(342.dp, 121.dp),
                    )
                    // Box-47:1247-componenteAyS
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 14.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1247;58:895-iconoAyS
                        Image(
                            painter = painterResource(id = R.drawable.ico_asistecia),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1247;58:896-Ayuda y Soporte
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Ayuda y Soporte",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Box-47:1248-componenteContactanos
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 49.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1248;58:895-iconoContactanos
                        Image(
                            painter = painterResource(id = R.drawable.ico_contactanos),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1248;58:896-Contactanos
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Contactanos",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Box-47:1249-componentePyS
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 86.dp)
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1249;58:895-iconoPyS
                        Image(
                            painter = painterResource(id = R.drawable.ico_politicas),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1249;58:896-Politica de Provacidad
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Politica de Privacidad ",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                // Box-47:1250-pI_seccion2
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 145.dp)
                        .size(342.dp, 86.dp),
                ) {
                    // Empty-47:1251-Rectangle 52
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .advancedShadow(
                                color = Color(0x3f000000),
                                alpha = 0.25f,
                                cornersRadius = 8.dp,
                                shadowBlurRadius = 4.dp,
                                offsetX = 0.dp,
                                offsetY = 1.dp
                            )
                            .background(Color(0xff295e84), RoundedCornerShape(8.dp))
                            .size(342.dp, 86.dp),
                    )
                    // Box-47:1252-componenteSeguridad
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 14.dp)
                            .background(Color(0xff295e84))
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1252;58:895-iconoSeguridad
                        Image(
                            painter = painterResource(id = R.drawable.ico_seguridad),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1252;58:896-Seguridad
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Seguridad",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Box-47:1253-componenteTema
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 16.dp, y = 49.dp)
                            .background(Color(0xff295e84))
                            .size(277.dp, 24.dp),
                    ) {
                        // Image-I47:1253;58:895-iconoTema
                        Image(
                            painter = painterResource(id = R.drawable.ico_tema),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(24.dp, 24.dp)
                                .clipToBounds(),
                        )
                        // Text-I47:1253;58:896-Tema
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 37.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Tema",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                        // Text-I47:1253;58:897-Modo Claro
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentHeight()
                                .offset(x = 62.dp, y = 2.dp)
                                .width(241.dp),
                            text = "Modo Claro",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            overflow = TextOverflow.Ellipsis,
                        )
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
            cornerRadius = CornerRadius(cornersRadius.toPx()),
            size = size
        )
    }
)

