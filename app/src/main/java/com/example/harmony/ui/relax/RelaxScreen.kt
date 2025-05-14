package com.example.harmony.ui.relax

import Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.example.harmony.ui.components.Container_Ejercicio
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.home.ScreenContent
import com.example.harmony.ui.theme.DarkerPurpleColor
import com.example.harmony.ui.theme.PurpleColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelaxScreen(navController: NavHostController, relaxViewModel: RelaxViewModel) {
    val context = LocalContext.current


    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.relajacion)
    val inicio = context.getString(R.string.inicio)
    val relajacion = context.getString(R.string.relajacion)

    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = PurpleColor,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(navController = navController, drawerActions = relaxViewModel)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.background_relajacion),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    RelaxTopBar(
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
                    NavigationBar(
                        modifier = Modifier.height(80.dp),
                        containerColor = DarkerPurpleColor
                    ) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.home_unselected),
                                    contentDescription = "Home",
                                    tint = Color.White
                                )
                                   },
                            label = { Text(inicio, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = { navController.navigate("main") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
                                selectedIconColor = Color.Transparent,
                                unselectedIconColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.relax_selected),
                                    contentDescription = "Relaxing",
                                    tint = Color.White
                                )
                                   },
                            label = { Text(relajacion, color = Color.White) },
                            selected = true,
                            onClick = {  },
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

            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RelaxScreenContent(navController = navController)
                }

            } // Scaffold
        } // Box
    } // ModalNavigationDrawer
} // fun

@Composable
fun RelaxScreenContent(navController: NavHostController) {
    Spacer(Modifier.height(16.dp))
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp) // Opcional: padding alrededor del contenido
    ) {
        item {
            Container_Ejercicio(
                Titulo = "Respiración",
                Subtitulo = "4-7-8",
                Duracion = "1-2 MIN",
                Imagen = R.drawable.relajacion_imagen1,
                OnClick = { }
            )
        }
        item {
            Container_Ejercicio(
                Titulo = "Relajación",
                Subtitulo = "Muscular",
                Duracion = "5-10 MIN",
                Imagen = R.drawable.relajacion_imagen2,
                OnClick = { }
            )
        }
        item {
            Container_Ejercicio(
                Titulo = "Técnica de",
                Subtitulo = "la caja",
                Duracion = "2-3 MIN",
                Imagen = R.drawable.relajacion_imagen3,
                OnClick = { }
            )
        }
        item {
            Container_Ejercicio(
                Titulo = "Mindfulness",
                Subtitulo = "Aquí y ahora",
                Duracion = "3-5 MIN",
                Imagen = R.drawable.relajacion_imagen4,
                OnClick = { }
            )
        }
        item {
            Container_Ejercicio(
                Titulo = "Técnica",
                Subtitulo = "5-4-3-2-1",
                Duracion = "2-4 MIN",
                Imagen = R.drawable.relajacion_imagen5,
                OnClick = { }
            )
        }
        item {
            Container_Ejercicio(
                Titulo = "Escaneo",
                Subtitulo = "Corporal",
                Duracion = "5-7 MIN",
                Imagen = R.drawable.relajacion_imagen6,
                OnClick = { }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RelaxPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    RelaxScreen(navController = navController, relaxViewModel = RelaxViewModel(RelaxModel(context), context))
}