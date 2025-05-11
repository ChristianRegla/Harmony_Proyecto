package com.example.harmony.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.harmony.ui.components.SystemBarStyle

private val DarkColorScheme = darkColorScheme(
    primary = BlueMedium,
    secondary = Magenta,
    tertiary = Purple,
    background = BlueDark,
    surface = BlueNavy,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = darkColorScheme(
    primary = BlueMedium,
    secondary = Magenta,
    tertiary = Purple,
    background = BlueDark,
    surface = BlueNavy,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun HarmonyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Aplica edge-to-edge y estilo por defecto (transparente con íconos claros)
    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
        darkIcons = !darkTheme // Íconos oscuros en modo claro, claros en modo oscuro
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HarmonyTypography,
        content = content
    )
}
