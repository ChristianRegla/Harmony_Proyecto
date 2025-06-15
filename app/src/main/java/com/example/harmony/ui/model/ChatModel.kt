package com.example.harmony.ui.model

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.viewModel.ChatViewModel
import com.example.harmony.ui.screens.ChatScreen
import com.example.harmony.ui.theme.HarmonyTheme
import com.example.harmony.ui.theme.Magenta

@OptIn(ExperimentalMaterial3Api::class)
class ChatModel : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.auto(
                Magenta.hashCode(),
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            HarmonyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ChatScreen(navController = navController, chatViewModel = chatViewModel)
                }
            }
        }
    }
}