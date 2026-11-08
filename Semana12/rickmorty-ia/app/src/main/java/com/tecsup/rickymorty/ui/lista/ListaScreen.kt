package com.tecsup.rickymorty.ui.lista

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecsup.rickymorty.R
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.*

@Composable
fun ListaScreen(
    estado: UiState<List<Item>>,
    busqueda: String,
    aviso: String?,
    buscar: (String) -> Unit,
    reintentar: () -> Unit,
    abrir: (Int) -> Unit,
    alternarFavorito: (Int) -> Unit,
    cerrarSesion: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EncabezadoCatalogo("Rick y Morty", "Catálogo de personajes vivos", R.drawable.ic_portal) {
            TextButton(onClick = cerrarSesion) { Text("Salir") }
        }
        OutlinedTextField(
            value = busqueda,
            onValueChange = buscar,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Buscar por nombre") },
            placeholder = { Text("Ejemplo: Rick Sánchez") },
            leadingIcon = { Icon(painterResource(R.drawable.ic_buscar), null) },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { buscar("") }) {
                        Icon(painterResource(R.drawable.ic_cerrar), "Limpiar búsqueda")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
        if (aviso != null) {
            Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.secondaryContainer) {
                Row(Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("$aviso Mostrando datos guardados.", Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                    TextButton(onClick = reintentar) { Text("Reintentar") }
                }
            }
        }
        Box(Modifier.weight(1f)) {
            when (estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorView(estado.mensaje, reintentar)
                is UiState.Exito -> if (estado.datos.isEmpty()) {
                    val hayBusqueda = busqueda.isNotBlank()
                    EstadoVacioCatalogo(
                        R.drawable.ic_buscar,
                        if (hayBusqueda) "Sin coincidencias" else "Catálogo vacío",
                        if (hayBusqueda) "Prueba con otro nombre." else "Todavía no hay personajes para mostrar.",
                        if (hayBusqueda) "Limpiar búsqueda" else "Actualizar",
                        if (hayBusqueda) { { buscar("") } } else reintentar
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            if (busqueda.isBlank()) "Personajes · ${estado.datos.size}" else "Resultados · ${estado.datos.size}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        CuadriculaPersonajes(estado.datos, abrir, alternarFavorito, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
