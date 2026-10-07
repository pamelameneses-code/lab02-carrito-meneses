package com.tecsup.mibodega.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF1B1B1B),
    surface = Color.White,
    onSurface = Color(0xFF1B1B1B),
    surfaceVariant = GrisClaro,
    onSurfaceVariant = Color(0xFF5F5F5F)
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeBodega,
    onPrimary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E6E6),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE6E6E6),
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFB5B5B5)
)

@Composable
fun BodegaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro,
        content = content
    )
}