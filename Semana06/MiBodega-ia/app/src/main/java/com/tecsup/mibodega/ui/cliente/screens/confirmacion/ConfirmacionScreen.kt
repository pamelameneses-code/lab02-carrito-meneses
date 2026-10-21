package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.componentes.IconoMetodoPago
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun ConfirmacionScreen(
    carrito: List<ItemCarrito>,
    numeroPedido: Int,
    direccion: String,
    metodoPago: String,
    onVerEstado: () -> Unit,
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
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(VerdeBodega, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(56.dp)
            )
        }
        Text(
            "¡Pedido realizado!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeBodega
        )
        Text("Tu pedido está siendo preparado y será entregado pronto.")

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Pedido #$numeroPedido", style = MaterialTheme.typography.titleLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total")
                    Text(
                        "S/ %.2f".format(total),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text("Dirección", style = MaterialTheme.typography.labelMedium)
                Text(direccion, fontWeight = FontWeight.Medium)
                Text("Método de pago", style = MaterialTheme.typography.labelMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconoMetodoPago(metodoPago)
                    Text(
                        metodoPago,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        OutlinedButton(onClick = onVerEstado, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.Receipt, contentDescription = null, tint = VerdeBodega)
            Text("  Ver estado del pedido", color = VerdeBodega)
        }

        Button(
            onClick = onVolverAlInicio,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = VerdeBodega)
        ) {
            Text("Volver al inicio")
        }
    }
}