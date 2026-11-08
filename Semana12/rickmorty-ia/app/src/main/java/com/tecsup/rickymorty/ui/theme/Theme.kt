package com.tecsup.rickymorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val oscuro = darkColorScheme(
    primary = Color(0xFFA6EDA4),
    onPrimary = Color(0xFF123915),
    primaryContainer = Color(0xFF204C2F),
    onPrimaryContainer = Color(0xFFC5F4C6),
    secondaryContainer = Color(0xFF253E38),
    onSecondaryContainer = Color(0xFFC9E6DC),
    background = Color(0xFF101817),
    onBackground = Color(0xFFE5EEEA),
    surface = Color(0xFF101817),
    onSurface = Color(0xFFE5EEEA),
    surfaceContainerLow = Color(0xFF192523),
    surfaceContainer = Color(0xFF1C2B27),
    onSurfaceVariant = Color(0xFFB6C8BF),
    outline = Color(0xFF85988E),
    outlineVariant = Color(0xFF3C5149)
)

private val claro = lightColorScheme(
    primary = Color(0xFF246435),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC9F1CE),
    onPrimaryContainer = Color(0xFF10391C),
    secondaryContainer = Color(0xFFDCECE6),
    onSecondaryContainer = Color(0xFF203D33),
    background = Color(0xFFF5F9F5),
    onBackground = Color(0xFF17241D),
    surface = Color(0xFFF5F9F5),
    onSurface = Color(0xFF17241D),
    surfaceContainerLow = Color(0xFFEDF5EF),
    surfaceContainer = Color(0xFFE8F1EB),
    onSurfaceVariant = Color(0xFF46584E),
    outline = Color(0xFF6F8376),
    outlineVariant = Color(0xFFC6D6CB)
)

@Composable
fun RickMortyCatalogoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(colorScheme = if (darkTheme) oscuro else claro, typography = Typography, content = content)
}
