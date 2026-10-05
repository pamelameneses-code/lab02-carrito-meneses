package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosEntregaScreen(
    carrito: List<ItemCarrito>,
    onVolver: () -> Unit,
    onConfirmarPedido: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    val metodos = listOf("Efectivo al entregar", "Yape", "Plin")
    var metodoPago by remember { mutableStateOf(metodos[0]) }

    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
    val delivery = 4.0
    val total = subtotal + delivery

    val datosCompletos = nombre.isNotBlank() && telefono.isNotBlank() && direccion.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Datos de entrega") },
                navigationIcon = { TextButton(onClick = onVolver) { Text("Volver") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = telefono, onValueChange = { telefono = it },
                label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = direccion, onValueChange = { direccion = it },
                label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = referencia, onValueChange = { referencia = it },
                label = { Text("Referencia") }, modifier = Modifier.fillMaxWidth()
            )

            Text("Método de pago")
            metodos.forEach { metodo ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = metodoPago == metodo,
                        onClick = { metodoPago = metodo }
                    )
                    Text(metodo)
                }
            }

            Text("Subtotal: S/ %.2f".format(subtotal))
            Text("Delivery: S/ %.2f".format(delivery))
            Text("Total: S/ %.2f".format(total))

            Button(
                onClick = onConfirmarPedido,
                enabled = datosCompletos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar pedido")
            }
        }
    }
}
