package com.example.harmony.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmony.R
import com.example.harmony.ui.theme.BlueDark


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEditar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BlueDark //.copy(alpha = 0.95f) si queremos un fondo más transparente
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 35.dp)
            )
        },
    )
}