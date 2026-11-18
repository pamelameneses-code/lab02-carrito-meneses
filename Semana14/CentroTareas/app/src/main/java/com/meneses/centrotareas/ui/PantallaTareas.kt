package com.meneses.centrotareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.meneses.centrotareas.viewmodel.TareasViewModel

@Composable
fun PantallaTareas(vm: TareasViewModel, modifier: Modifier = Modifier) {
    val mensaje by vm.mensaje.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Surface(color = MaterialTheme.colorScheme.primary) {
            Text("CentroTareas", Modifier.fillMaxWidth().padding(16.dp),
                style = MaterialTheme.typography.headlineSmall)
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Tarjeta("Tarea con progreso") {
                Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                Button(onClick = vm::tareaLarga) { Text("Tarea larga") }
                Text(mensaje)
            }
            Tarjeta("Contar números primos") {

            }
            Tarjeta("Dos cargas") {

            }
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(titulo, color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium)
            contenido()
        }
    }
}
