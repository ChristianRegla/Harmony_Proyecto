package com.example.harmony.ui.ejercicios


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.components.TopBarEditar

@Composable
fun EjerciciosScreen(navController: NavHostController, ejerciciosViewModel: EjerciciosViewModel) {
    val context = LocalContext

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_ejercicios),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(
            topBar = {
                TopBarEditar(
                    onBackClick = { navController.popBackStack() },
                    title = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        ) { innerpadding ->
            EjerciciosContent(modifier = Modifier.fillMaxSize().padding(innerpadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EjerciciosContent(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val context = LocalContext.current
    EjerciciosScreen(navController = navController, ejerciciosViewModel = EjerciciosViewModel(EjerciciosModel(context), context))
}