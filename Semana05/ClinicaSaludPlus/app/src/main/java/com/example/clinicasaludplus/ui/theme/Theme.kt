package com.example.clinicasaludplus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MedicalLightColorScheme = lightColorScheme(
    primary = MedicalBlue,
    onPrimary = PureWhite,
    secondary = MedicalTurquoise,
    onSecondary = PureWhite,
    background = PureWhite,
    onBackground = DarkText,
    surface = CardBackground,
    onSurface = DarkText
)

@Composable
fun ClinicaSaludPlusTheme(
    // Forzamos false en dynamicColor para que SIEMPRE use los colores de la clínica
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MedicalLightColorScheme,
        typography = Typography,
        content = content
    )
}