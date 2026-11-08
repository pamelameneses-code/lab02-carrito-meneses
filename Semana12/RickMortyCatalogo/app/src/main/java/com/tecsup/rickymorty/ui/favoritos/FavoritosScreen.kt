package com.tecsup.rickymorty.ui.favoritos

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.lista.ListaScreen

@Composable
fun FavoritosScreen(estado: UiState<List<Item>>, volver: () -> Unit, abrir: (Int) -> Unit) {
    Column {
        TextButton(onClick = volver) { Text("Volver al catálogo") }
        Text("Favoritos")
        ListaScreen(estado, reintentar = {}, abrir = abrir, mensajeVacio = "Aún no tienes favoritos.")
    }
}
