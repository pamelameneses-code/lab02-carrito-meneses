package com.meneses.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meneses.avisoscampus.model.Aviso

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Tarjeta("Historial") {
        if (avisos.isEmpty()) Text("Sin avisos todavía")
        avisos.take(20).forEach { aviso ->
            HorizontalDivider()
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(aviso.origen.texto, color = MaterialTheme.colorScheme.primary)
                    Text(aviso.hora)
                }
                Text(aviso.titulo, style = MaterialTheme.typography.titleSmall)
                Text(aviso.texto)
            }
        }
    }
}
