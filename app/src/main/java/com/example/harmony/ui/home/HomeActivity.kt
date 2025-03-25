package com.example.harmony.ui.home

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
class HomeActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            HarmonyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen(navController = navController, homeViewModel = homeViewModel)
                }
            }
        }
    }
}