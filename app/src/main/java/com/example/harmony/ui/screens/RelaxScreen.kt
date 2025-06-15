package com.example.harmony.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.Container_Ejercicio
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.theme.PurpleColor
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import com.example.harmony.ui.model.RelaxModel
import com.example.harmony.ui.components.RelaxTopBar
import com.example.harmony.ui.viewModel.RelaxViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelaxScreen(navController: NavHostController, relaxViewModel: RelaxViewModel) {
    val context = LocalContext.current

    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.relajacion)

    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SystemBarStyle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = PurpleColor,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(
                    navController = navController,
                    drawerActions = relaxViewModel,
                    isDrawerOpen = drawerState.isOpen,
                    onCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
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
                                if (drawerState.isClosed) drawerState.open()
                            }
                        },
                        title = headerTitle,
                        navController = navController,
                        modifier = Modifier.wrapContentHeight()
                    )
                },
                containerColor = Color.Transparent,
                contentColor = Color.White

            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RelaxScreenContent(navController = navController)
                }

            } // Scaffold
        } // Box
    } // ModalNavigationDrawer
} // fun

val listaDeEjercicios = listOf(
    EjercicioData("Respiración", "4-7-8", "1-2 MIN", R.drawable.relajacion_imagen1, onClickAction = {  }),
    EjercicioData("Relajación", "Muscular", "5-10 MIN", R.drawable.relajacion_imagen2, onClickAction = {  }),
    EjercicioData("Técnica de", "la caja", "2-3 MIN", R.drawable.relajacion_imagen3, onClickAction = {  }),
    EjercicioData("Mindfulness", "Aquí y ahora", "3-5 MIN", R.drawable.relajacion_imagen4, onClickAction = {  }),
    EjercicioData("Técnica", "5-4-3-2-1", "2-4 MIN", R.drawable.relajacion_imagen5, onClickAction = {  }),
    EjercicioData("Escaneo", "Corporal", "5-7 MIN", R.drawable.relajacion_imagen6, onClickAction = {  })
)

@Composable
fun RelaxScreenContent(navController: NavHostController) {
    val ejercicios = remember {
        listOf(
            EjercicioData("Respiración", "4-7-8", "1-2 MIN", R.drawable.relajacion_imagen1, onClickAction = { navController.navigate("ejercicios") }),
            EjercicioData("Relajación", "Muscular", "5-10 MIN", R.drawable.relajacion_imagen2, onClickAction = {  }),
            EjercicioData("Técnica de", "la caja", "2-3 MIN", R.drawable.relajacion_imagen3, onClickAction = {  }),
            EjercicioData("Mindfulness", "Aquí y ahora", "3-5 MIN", R.drawable.relajacion_imagen4, onClickAction = {  }),
            EjercicioData("Técnica", "5-4-3-2-1", "2-4 MIN", R.drawable.relajacion_imagen5, onClickAction = {  }),
            EjercicioData("Escaneo", "Corporal", "5-7 MIN", R.drawable.relajacion_imagen6, onClickAction = {  })
            // ... más ejercicios
        )
    }

    val groupedEjercicios: List<List<EjercicioData>> = listaDeEjercicios.chunked(2)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(items = groupedEjercicios) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { ejercicio ->
                    Box(modifier = Modifier.weight(1f)) {
                        Container_Ejercicio(
                            Titulo = ejercicio.titulo,
                            Subtitulo = ejercicio.subtitulo,
                            Duracion = ejercicio.duracion,
                            Imagen = ejercicio.imagenResId,
                            onClick = ejercicio.onClickAction
                        )
                    }
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

data class EjercicioData(
    val titulo: String,
    val subtitulo: String,
    val duracion: String,
    val imagenResId: Int,
    val onClickAction: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun RelaxPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    RelaxScreen(navController = navController, relaxViewModel = RelaxViewModel(
        RelaxModel(context),
        context
    )
    )
}