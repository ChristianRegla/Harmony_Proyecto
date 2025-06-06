package com.example.harmony.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.harmony.R
import com.example.harmony.ui.theme.BlueDark


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onOpenDrawer: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BlueDark //.copy(alpha = 0.95f) si queremos un fondo más transparente
        ),
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(25.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.lobster)),
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                )
        },
        actions = {
            IconButton(onClick = { navController.navigate("chatbot") })
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Chat",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(25.dp)
                )
            }
        }
    )
}