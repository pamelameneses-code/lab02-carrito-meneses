package com.tecsup.rickymorty.ui.favoritos

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.rickymorty.R
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.*

@Composable
fun FavoritosScreen(
    estado: UiState<List<Item>>,
    volver: () -> Unit,
    abrir: (Int) -> Unit,
    alternarFavorito: (Int) -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val cantidad = (estado as? UiState.Exito)?.datos?.size
        EncabezadoCatalogo(
            "Favoritos",
            when (cantidad) {
                null, 0 -> "Tu colección de personajes"
                1 -> "1 personaje guardado"
                else -> "$cantidad personajes guardados"
            },
            R.drawable.ic_favorito
        )
        Box(Modifier.weight(1f)) {
            when (estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> EstadoVacioCatalogo(R.drawable.ic_favorito_borde, "Favoritos no disponibles", estado.mensaje, "Ver catálogo", volver)
                is UiState.Exito -> if (estado.datos.isEmpty()) {
                    EstadoVacioCatalogo(
                        R.drawable.ic_favorito_borde, "Aún no tienes favoritos",
                        "Toca el corazón de un personaje para guardarlo aquí.", "Explorar catálogo", volver
                    )
                } else {
                    CuadriculaPersonajes(estado.datos, abrir, alternarFavorito)
                }
            }
        }
    }
}
