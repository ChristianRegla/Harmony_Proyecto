package com.example.harmony.ui.relax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.theme.HarmonyTheme
import com.example.harmony.ui.theme.PurpleColor

@OptIn(ExperimentalMaterial3Api::class)
class RelaxModel : ComponentActivity() {

    private val relaxViewModel: RelaxViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                PurpleColor.hashCode(),
                android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            HarmonyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RelaxScreen(navController = navController, relaxViewModel = relaxViewModel)
                }
            }
        }
    }
}