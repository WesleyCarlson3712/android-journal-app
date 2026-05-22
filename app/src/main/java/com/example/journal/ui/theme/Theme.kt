package com.example.journal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC5),

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),

    onPrimary = Color.Black,
    onSecondary = Color.Black,

    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF6750A4),
    secondary = Color(0xFF625B71),

    background = Color(0xFFF5F5F5),
    surface = Color.White,

    onPrimary = Color.White,
    onSecondary = Color.White,

    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun JournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        }
        else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}