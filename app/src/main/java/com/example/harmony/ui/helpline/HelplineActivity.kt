package com.example.harmony.ui.helpline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.home.HomeScreen
import com.example.harmony.ui.theme.HarmonyTheme

class HelplineActivity : ComponentActivity() {

    private val HelplineViewModel: HelplineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val NavController = rememberNavController()
            HarmonyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen(navController = navController, helplineViewModel = helplineViewModel)
                }
            }
        }
    }
}