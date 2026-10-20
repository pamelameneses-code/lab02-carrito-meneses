package com.tecsup.cafecampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// Paleta semántica de las ilustraciones. Los colores quedan centralizados en el tema.
data class PaletaAlimentos(
    val crema: Color, val pan: Color, val cafe: Color, val naranja: Color,
    val rojo: Color, val verde: Color, val plato: Color,
    val fondoCafe: Color, val fondoPan: Color, val fondoJugo: Color
)
@Composable fun paletaAlimentos(): PaletaAlimentos {
    val oscuro = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if(oscuro) PaletaAlimentos(
        Color(0xFF51402B),Color(0xFFEFB85B),Color(0xFF9D673D),Color(0xFFFF941D),
        Color(0xFFF06A67),Color(0xFF55BB72),Color(0xFFE6DDD0),
        Color(0xFF43372C),Color(0xFF454022),Color(0xFF513820)
    ) else PaletaAlimentos(
        Color(0xFFFFEDD5),Color(0xFFF5A800),Color(0xFF7B4B26),Color(0xFFFF800A),
        Color(0xFFDC2626),Color(0xFF16A34A),Color(0xFFFFFCF4),
        Color(0xFFE7D7C3),Color(0xFFFEF4C3),Color(0xFFFFE5CA)
    )
}
