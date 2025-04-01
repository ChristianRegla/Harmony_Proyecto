package com.example.harmony.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.harmony.ui.home.HomeScreen
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.ScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController, ChatViewModel: ChatViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background_chatbot),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                ChatTopBar(
                    onBackClick = { navController.popBackStack() },
                    title = context.getString(R.string.chatbot),
                    modifier = Modifier.size(56.dp)
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) { padding ->
            ScreenContent(
                navController = navController,modifier = Modifier.padding(padding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatbotPreview() {
    val navController = rememberNavController()
    ChatScreen(navController = navController, ChatViewModel = ChatViewModel())
}