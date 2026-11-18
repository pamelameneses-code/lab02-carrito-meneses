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
import com.meneses.centrotareas.model.CargaUi
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
    val carga by vm.carga.collectAsState()
    val tiemposCarga by vm.tiemposCarga.collectAsState()
    val simularError by vm.simularError.collectAsState()
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
                        enabled = hasta != null && primos !is PrimosUi.Calculando) { Text("Contar") }
                    OutlinedButton(onClick = vm::cancelarPrimos,
                        enabled = primos is PrimosUi.Calculando) { Text("Cancelar") }
                }
                when (val estado = primos) {
                    PrimosUi.Inicial -> Unit
                    is PrimosUi.Calculando -> {
                        LinearProgressIndicator(
                            progress = { estado.porcentaje / 100f },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Calculando… ${estado.porcentaje} %")
                    }
                    PrimosUi.Cancelado -> Text("Cancelado")
                    is PrimosUi.Listo -> Resultado("${estado.cantidad} primos · ${estado.milisegundos} ms")
                }
            }
            Tarjeta("Dos cargas") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Switch(checked = simularError, onCheckedChange = vm::cambiarSimularError,
                        enabled = carga != CargaUi.Cargando)
                    Text("Simular error")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = vm::cargarUnaPorUna, enabled = carga != CargaUi.Cargando) {
                        Text("Una por una")
                    }
                    Button(onClick = vm::cargarALaVez, enabled = carga != CargaUi.Cargando) {
                        Text("A la vez")
                    }
                }
                when (val estado = carga) {
                    CargaUi.Inicial -> Unit
                    CargaUi.Cargando -> CircularProgressIndicator()
                    is CargaUi.Error -> Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(estado.mensaje)
                            OutlinedButton(onClick = vm::cargarALaVez) { Text("Reintentar") }
                        }
                    }
                    is CargaUi.Exito -> Resultado(
                        "${estado.modo}\n${estado.perfil}\nPedidos: ${estado.pedidos} · ${estado.milisegundos} ms"
                    )
                }
                if (tiemposCarga.isNotEmpty()) {
                    TablaTiempos(tiemposCarga)
                }
            }
        }
    }
}

@Composable
private fun TablaTiempos(tiempos: Map<String, List<Long>>) {
    Text("Tiempos de carga", style = MaterialTheme.typography.titleSmall)
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (modo in listOf("Una por una", "A la vez")) {
            Column(Modifier.weight(1f)) {
                Text(modo)
                val mediciones = tiempos[modo].orEmpty()
                if (mediciones.isEmpty()) Text("—")
                mediciones.asReversed().forEach { Text("$it ms") }
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
