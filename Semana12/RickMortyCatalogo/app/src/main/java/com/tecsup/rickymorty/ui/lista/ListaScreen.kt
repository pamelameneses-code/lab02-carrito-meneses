package com.tecsup.rickymorty.ui.lista

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.Cargando
import com.tecsup.rickymorty.ui.components.ErrorView

@Composable
fun ListaScreen(
    estado: UiState<List<Item>>,
    reintentar: () -> Unit,
    abrir: (Int) -> Unit,
    modifier: Modifier = Modifier,
    mensajeVacio: String = "No hay personajes para mostrar."
) {
    Column(modifier.padding(16.dp)) {
        when (estado) {
            UiState.Cargando -> Cargando()
            is UiState.Error -> ErrorView(estado.mensaje, reintentar)
            is UiState.Exito -> {
                if (estado.datos.isEmpty()) Text(mensajeVacio)
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(estado.datos, key = { it.id }) { item ->
                        Card(Modifier.fillMaxWidth().clickable { abrir(item.id) }) {
                            Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                AsyncImage(
                                    model = item.imagenUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.small)
                                )
                                Column {
                                    Text(item.titulo, style = MaterialTheme.typography.titleMedium)
                                    Text(item.subtitulo)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
