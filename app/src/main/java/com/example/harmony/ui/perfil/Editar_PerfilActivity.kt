package com.example.harmony.ui.perfil
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.theme.HarmonyTheme

@OptIn(ExperimentalMaterial3Api::class)
class Editar_PerfilActivity  : ComponentActivity(){
    private val  Editar_PerfilViewModel: Editar_PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            HarmonyTheme {
                Surface(color = MaterialTheme.colorScheme.background){
                    Editar_PerfilScreen(navController = navController, Editar_PerfilViewModel = Editar_PerfilViewModel)
                }
            }
        }

    }
}