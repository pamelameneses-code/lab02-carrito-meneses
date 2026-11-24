package com.meneses.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meneses.murocampus.viewmodel.PostsViewModel
import com.meneses.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(vm: PostsViewModel, id: Int, modifier: Modifier = Modifier) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    Box(modifier) {
        when (val actual = estado) {
            UiState.Cargando -> Cargando("Cargando detalle…")
            is UiState.Error -> ErrorConReintento(actual.mensaje) { vm.abrir(id) }
            is UiState.Exito -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val (post, comentarios) = actual.datos
                item { Text(post.title, style = MaterialTheme.typography.headlineSmall) }
                item { Text(post.body) }
                item { Text("Comentarios (${comentarios.size})", style = MaterialTheme.typography.titleLarge) }
                items(comentarios, key = { it.id }) { comentario ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(comentario.email, color = MaterialTheme.colorScheme.primary)
                        Text(comentario.body)
                    }
                }
            }
        }
    }
}
