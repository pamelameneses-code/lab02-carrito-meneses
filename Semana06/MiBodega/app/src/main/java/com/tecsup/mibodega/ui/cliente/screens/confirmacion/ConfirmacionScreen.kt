package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito

@Composable
fun ConfirmacionScreen(
    carrito: List<ItemCarrito>,
    onVolverAlInicio: () -> Unit
) {
    val total = carrito.sumOf { it.producto.precio * it.cantidad } + 4.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("✅", style = MaterialTheme.typography.displayLarge)
        Text("¡Pedido realizado!", style = MaterialTheme.typography.headlineMedium)
        Text("Total: S/ %.2f".format(total))
        Text("Tu pedido está siendo preparado y será entregado pronto.")

        Button(onClick = onVolverAlInicio, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al inicio")
        }
    }
}