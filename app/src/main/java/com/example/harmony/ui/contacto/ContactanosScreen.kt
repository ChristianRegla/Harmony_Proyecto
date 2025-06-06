package com.example.harmony.ui.contacto

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.theme.BlueDark
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx. compose. foundation. layout. size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.components.TopBarEditar
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactanosScreen(navController: NavHostController, contactanosViewModel: ContactanosViewModel) {
    // Para los textos y que estén traducidos:
    val relajacion = stringResource(R.string.relajacion)
    val home = stringResource(R.string.inicio)
    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                DrawerContentComponent(navController = navController, drawerActions = contactanosViewModel, isDrawerOpen = drawerState.isOpen)
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
                    TopBarEditar(
                        onBackClick = { navController.popBackStack() },
                        title = "",
                        modifier = Modifier.size(20.dp)
                    )
                },
                // Barra de abajo
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier.height(80.dp),
                        containerColor = BlueDark
                    ) {
                        NavigationBarItem(
                            icon = { Icon(
                                painterResource(id = R.drawable.home_unselected),
                                contentDescription = "Home",
                                tint = Color.White
                            ) },
                            label = { Text(home, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = {navController.navigate("main")},
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
                                selectedIconColor = Color.Transparent,
                                unselectedIconColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(
                                painterResource(id = R.drawable.relax_unselected),
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
                ContactContent(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding),
                    navController = navController
                )
            }
        }
    }
}
@Composable
fun ContactContent(navController: NavController, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val titulo = stringResource(R.string.contactanos)
    val emailMensaje = stringResource(R.string.Nuestro_equipo)
    val seguidores1 = stringResource(R.string.Seguidores1)
    val seguidores2 = stringResource(R.string.Seguidores2)
    val seguidores3 = stringResource(R.string.Seguidores3)
    Column(modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(scrollState)){

        // Contenido de la pantalla de contacto
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .background(Color(0x00ffffff), RoundedCornerShape(12.dp))
                .size(375.dp, 734.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(12.dp)),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .wrapContentSize()
                    .offset(x = 0.dp, y = 45.dp),
                text = titulo,
                color = Color(0xffffffff),
                fontSize = 42.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Cuadro de Email
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(350.dp, 165.dp)
                    .offset(x = 0.dp, y = 140.dp)
                    .background(Color(0xffffffff), RoundedCornerShape(25.dp))

            ) {
                // Nuestro equipo esta en lineaLun-Vie • 9-17
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentHeight()
                        .offset(x = 90.dp, y = 116.dp)
                        .width(194.dp),
                    text = emailMensaje,
                    color = Color(0xff000000),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 20.dp)
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                        .clickable {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = "mailto:a22310380@ceti.mx".toUri() // Correo Electronico
                            }
                            context.startActivity(intent)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mail),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(30.dp) // tamaño interno del ícono
                    )
                }
            }
            // Text-Email
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .wrapContentSize()
                    .offset(x = 1.5.dp, y = 225.dp),
                text = "Email",
                color = Color(0xff000000),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
            )
            // Instagram
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 326.dp)
                    .size(343.dp, 80.dp),
            ) {
                // Cuadro de Instagram
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color(0xffffffff), RoundedCornerShape(15.dp))
                        .size(343.dp, 80.dp)
                        .border(1.dp, Color(0xffa7a7a7), RoundedCornerShape(15.dp)),
                )
                // Icono de Instagram
                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = 15.dp, y = 0.dp)
                        .size(50.dp, 50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                // Text-Instagram
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 17.dp),
                    text = "Instagram",
                    color = Color(0xff000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Text-Seguidores • 118 Pub
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 42.dp),
                    text = seguidores1,
                    color = Color(0xff717171),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Icono de viajar
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 281.dp, y = 18.dp)
                        .size(44.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFF377BAC))
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                "https://www.instagram.com/christian_slappy?igsh=MXhtMm02ejRxYjUxbg==".toUri()) // Reemplaza con el perfil real
                            context.startActivity(intent)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ingresar),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp) // tamaño interno del ícono
                    )
                }
            }
            // Telegram
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 416.dp)
                    .size(343.dp, 80.dp),
            ) {
                // Cuadro de Telegram
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color(0xffffffff), RoundedCornerShape(15.dp))
                        .size(343.dp, 80.dp)
                        .border(1.dp, Color(0xffa7a7a7), RoundedCornerShape(15.dp)),
                )
                // Icono de Telegram
                Image(
                    painter = painterResource(id = R.drawable.telegram),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 15.dp, y = 15.dp)
                        .size(50.dp, 50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                // Text-Telegram
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 17.dp),
                    text = "Telegram",
                    color = Color(0xff000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Text-1,3K Seguidores • 85 Pub
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 42.dp),
                    text = seguidores2,
                    color = Color(0xff717171),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Icono de viajar
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 281.dp, y = 18.dp)
                        .size(44.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFF377BAC))
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                "https://info.jalisco.gob.mx/instituciones/hospital-psiquiatrico-de-jalisco-estancia-prolongada".toUri()) // Reemplaza con el perfil real
                            context.startActivity(intent)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ingresar),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp) // tamaño interno del ícono
                    )
                }
            }
            // Facebook
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 506.dp)
                    .size(343.dp, 80.dp),
            ) {
                // Cuadro de Facebook
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color(0xffffffff), RoundedCornerShape(15.dp))
                        .size(343.dp, 80.dp)
                        .border(1.dp, Color(0xffa7a7a7), RoundedCornerShape(15.dp)),
                )
                // Icono de Facebook
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 15.dp, y = 15.dp)
                        .size(50.dp, 50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                // Text-Facebook
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 17.dp),
                    text = "Facebook",
                    color = Color(0xff000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Text-3,8K Seguidores • 136 Pub
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 81.dp, y = 42.dp),
                    text = seguidores3,
                    color = Color(0xff717171),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
                // Icono de viajar
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 281.dp, y = 18.dp)
                        .size(44.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFF377BAC))
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                "https://www.facebook.com/share/1Mk4xNzHmx/".toUri()) // Reemplaza con el perfil real
                            context.startActivity(intent)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ingresar),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp) // tamaño interno del ícono
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ContactanosPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ContactanosScreen(navController = navController, contactanosViewModel = ContactanosViewModel(ContactanosModel(context), context))
}