package com.meneses.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.meneses.campusgps.model.LugarConDistancia
import com.meneses.campusgps.util.formatearDistancia

@Composable
fun ListaLugares(lugares: List<LugarConDistancia>, seleccionado: Int?, radioMetros: Int, onClick: (Int) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth().height(220.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items(lugares, key = { it.lugar.id }) { entrada ->
            val lugar = entrada.lugar
            val activa = lugar.id == seleccionado
            val colores = MaterialTheme.colorScheme
            Card(
                onClick = { onClick(lugar.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = if (activa) colores.primaryContainer else colores.surfaceVariant),
                border = if (activa) BorderStroke(1.dp, colores.primary) else null
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("●", color = Color(0xFF0077B6), modifier = Modifier.padding(end = 8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(lugar.nombre, fontWeight = FontWeight.Bold)
                        Text(lugar.categoria, color = colores.onSurfaceVariant)
                        if (entrada.metros?.let { it <= radioMetros } == true) {
                            Text("Dentro del radio", color = colores.primary, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Text(entrada.metros?.let(::formatearDistancia) ?: "Sin calcular")
                }
            }
        }
    }
}
