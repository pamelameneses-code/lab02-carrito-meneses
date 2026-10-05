package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun IconoMetodoPago(metodo: String) {
    val (fondo, texto) = when {
        metodo.contains("Yape", ignoreCase = true) -> Color(0xFF722282) to "yape"
        metodo.contains("Plin", ignoreCase = true) -> Color(0xFF00C8E0) to "plin"
        else -> Color(0xFF2E7D32) to "S/"
    }
    Box(
        modifier = Modifier
            .background(fondo, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(texto, color = Color.White, fontWeight = FontWeight.Bold)
    }
}