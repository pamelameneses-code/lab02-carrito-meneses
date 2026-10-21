package com.tecsup.mibodega.ui.cliente.screens.pedidos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.componentes.BarraInferior
import com.tecsup.mibodega.ui.componentes.IconoMetodoPago
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun PedidosScreen(
    pedidos: List<Pedido>,
    onNavegarTab: (Int) -> Unit
) {
    Scaffold(
        bottomBar = { BarraInferior(seleccionado = 2, onNavegar = onNavegarTab) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Mis pedidos",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
            )

            if (pedidos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no tienes pedidos")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(pedidos) { pedido ->
                        PedidoCard(pedido)
                    }
                }
            }
        }
    }
}

@Composable
private fun PedidoCard(pedido: Pedido) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pedido #${pedido.numero}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "S/ %.2f".format(pedido.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "${pedido.cantidadProductos} productos",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = pedido.direccion,
                style = MaterialTheme.typography.bodyLarge
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconoMetodoPago(pedido.metodoPago)
                Text(
                    text = pedido.metodoPago,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(
                text = pedido.estado,
                style = MaterialTheme.typography.bodyLarge,
                color = VerdeBodega
            )
        }
    }
}