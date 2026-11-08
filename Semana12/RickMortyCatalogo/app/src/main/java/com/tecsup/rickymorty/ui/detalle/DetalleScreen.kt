package com.tecsup.rickymorty.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.Cargando
import com.tecsup.rickymorty.ui.components.ErrorView

@Composable
fun DetalleScreen(estado: UiState<Item>, volver: () -> Unit, reintentar: () -> Unit) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(onClick = volver) { Text("Volver") }
        when (estado) {
            UiState.Cargando -> Cargando()
            is UiState.Error -> ErrorView(estado.mensaje, reintentar)
            is UiState.Exito -> Column(
                Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val item = estado.datos
                AsyncImage(item.imagenUrl, item.titulo, Modifier.size(180.dp))
                Text(item.titulo, style = MaterialTheme.typography.headlineMedium)
                Text("Especie: ${item.subtitulo}")
                Text("Estado: ${if (item.estado == "Alive") "Vivo" else item.estado}")
                Text("Origen: ${item.origen}")
            }
        }
    }
}
