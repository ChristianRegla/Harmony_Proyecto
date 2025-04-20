package com.example.harmony.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val HarmonyTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp
    ),

    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 13.sp
    ),

    headlineLarge = TextStyle (
        fontFamily = FontFamily.Default,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    // Aquí podemos agregar más estilos de texto según sea necesario. (titleLarge, labelSmall, etc.)
    //Que bueno que me avisas porque igual lo iba a hacer
)