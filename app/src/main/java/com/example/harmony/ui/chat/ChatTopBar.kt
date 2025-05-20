package com.example.harmony.ui.chat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmony.R
import com.example.harmony.ui.theme.Magenta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    title: String,
    onBackClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Magenta.copy(alpha = 0.8f)
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Atras",
                    tint = Color.White,
                    modifier = Modifier
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
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            IconButton(onClick = onDeleteChatClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier
                )
            }
        }
    )
}