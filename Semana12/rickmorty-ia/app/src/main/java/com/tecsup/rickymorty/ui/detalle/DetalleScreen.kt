package com.tecsup.rickymorty.ui.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tecsup.rickymorty.R
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.Cargando
import com.tecsup.rickymorty.ui.components.ErrorView
import com.tecsup.rickymorty.ui.components.FotoPersonaje

@Composable
fun DetalleScreen(estado: UiState<Item>, volver: () -> Unit, reintentar: () -> Unit, alternarFavorito: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = volver) { Icon(painterResource(R.drawable.ic_volver), "Volver") }
            Text("Detalle del personaje", style = MaterialTheme.typography.titleLarge)
        }
        Box(Modifier.weight(1f)) {
            when (estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorView(estado.mensaje, reintentar)
                is UiState.Exito -> {
                    val item = estado.datos
                    Column(
                        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FotoPersonaje(item, Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(20.dp)))
                        Text(item.titulo, style = MaterialTheme.typography.headlineMedium)
                        val estadoVisible = when (item.estado) {
                            "Alive" -> "Vivo"
                            "Dead" -> "Muerto"
                            "unknown", "" -> "Desconocido"
                            else -> item.estado
                        }
                        OutlinedCard(shape = RoundedCornerShape(16.dp)) {
                            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Dato("Especie", item.subtitulo)
                                Dato("Estado", estadoVisible)
                                Dato("Origen", item.origen.ifBlank { "Desconocido" })
                            }
                        }
                        FilledTonalButton(onClick = alternarFavorito, modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                painterResource(if (item.esFavorito) R.drawable.ic_favorito else R.drawable.ic_favorito_borde), null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(if (item.esFavorito) "Quitar de favoritos" else "Guardar en favoritos")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Dato(etiqueta: String, texto: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(etiqueta, Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(texto, Modifier.weight(2f), style = MaterialTheme.typography.bodyLarge)
    }
}
