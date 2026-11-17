package com.tecsup.rickymorty.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.rickymorty.ui.UiState

@Composable
fun AdminScreen(
    estado: UiState<TotalesAdmin>,
    ocupado: Boolean,
    mensaje: String?,
    volver: () -> Unit,
    sincronizar: () -> Unit,
    limpiar: () -> Unit
) {
    Column(
        Modifier.verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextButton(onClick = volver) { Text("Volver al catálogo") }
        Text("Panel de administrador", style = MaterialTheme.typography.headlineMedium)
        when (estado) {
            UiState.Cargando -> Text("Cargando totales…")
            is UiState.Error -> Text(estado.mensaje)
            is UiState.Exito -> {
                Text("Total guardado: ${estado.datos.guardados}")
                Text("Total de favoritos: ${estado.datos.favoritos}")
            }
        }
        Button(onClick = sincronizar, enabled = !ocupado) { Text("Sincronizar") }
        OutlinedButton(onClick = limpiar, enabled = !ocupado) { Text("Limpiar caché") }
        if (ocupado) Text("Procesando…")
        if (mensaje != null) Text(mensaje)
    }
}
