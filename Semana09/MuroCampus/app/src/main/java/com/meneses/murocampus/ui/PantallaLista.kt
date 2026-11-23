package com.meneses.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meneses.murocampus.viewmodel.PostsViewModel
import com.meneses.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(vm: PostsViewModel, modifier: Modifier = Modifier, abrir: (Int) -> Unit = {}) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    androidx.compose.foundation.layout.Box(modifier) {
        when (val actual = estado) {
            UiState.Cargando -> Cargando("Cargando publicaciones…")
            is UiState.Error -> ErrorConReintento(actual.mensaje, vm::cargar)
            is UiState.Exito -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(actual.datos, key = { it.id }) { post ->
                    TarjetaPost(post) { abrir(post.id) }
                }
            }
        }
    }
}
