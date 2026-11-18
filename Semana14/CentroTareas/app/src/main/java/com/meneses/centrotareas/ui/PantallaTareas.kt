package com.meneses.centrotareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.meneses.centrotareas.model.PrimosUi
import com.meneses.centrotareas.viewmodel.TareasViewModel

@Composable
fun PantallaTareas(vm: TareasViewModel, modifier: Modifier = Modifier) {
    val mensaje by vm.mensaje.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }
    val progreso by vm.progreso.collectAsState()
    val corriendo by vm.corriendo.collectAsState()
    val primos by vm.primos.collectAsState()
    var limite by rememberSaveable { mutableStateOf("500000") }
    val hasta = limite.toIntOrNull()?.takeIf { it >= 2 }
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Surface(color = MaterialTheme.colorScheme.primary) {
            Text("CentroTareas", Modifier.fillMaxWidth().padding(16.dp),
                style = MaterialTheme.typography.headlineSmall)
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Tarjeta("Tarea con progreso") {
                Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = vm::tareaLarga, enabled = !corriendo) { Text("Iniciar") }
                    OutlinedButton(onClick = vm::cancelar, enabled = corriendo) { Text("Cancelar") }
                }
                LinearProgressIndicator(progress = { progreso }, modifier = Modifier.fillMaxWidth())
                Text("${(progreso * 100).toInt()} %")
                Text(mensaje)
            }
            Tarjeta("Contar números primos") {
                OutlinedTextField(
                    value = limite,
                    onValueChange = { if (it.all { caracter -> caracter in '0'..'9' }) limite = it },
                    label = { Text("Hasta") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { hasta?.let(vm::contar) },
                        enabled = hasta != null && primos != PrimosUi.Calculando) { Text("Contar") }
                    OutlinedButton(onClick = vm::cancelarPrimos,
                        enabled = primos == PrimosUi.Calculando) { Text("Cancelar") }
                }
                when (val estado = primos) {
                    PrimosUi.Inicial -> Unit
                    PrimosUi.Calculando -> Text("Calculando…")
                    PrimosUi.Cancelado -> Text("Cancelado")
                    is PrimosUi.Listo -> Resultado("${estado.cantidad} primos · ${estado.milisegundos} ms")
                }
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

@Composable
private fun Resultado(texto: String) {
    Surface(color = androidx.compose.ui.graphics.Color(0xFFD9F2DF),
        contentColor = androidx.compose.ui.graphics.Color(0xFF143D21),
        shape = MaterialTheme.shapes.small, modifier = Modifier.fillMaxWidth()) {
        Text(texto, Modifier.padding(12.dp))
    }
}
