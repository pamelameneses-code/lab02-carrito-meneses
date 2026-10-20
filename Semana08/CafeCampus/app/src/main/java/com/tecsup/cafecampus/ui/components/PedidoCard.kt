package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*

import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
@Composable fun PedidoCard(p: PedidoConDetalle, onClick: () -> Unit, acciones: @Composable ColumnScope.() -> Unit = {}) {
    CafeCard(Modifier.clickable(onClick=onClick)) {
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) {
            Text("Pedido #${p.pedido.id}",style=MaterialTheme.typography.titleMedium)
            EstadoChip(EstadoPedido.valueOf(p.pedido.estado))
        }
        Text("${p.cliente.nombre} · Recojo: ${p.pedido.horaRecojo}")
        Text("${p.detalles.sumOf { it.cantidad }} productos · ${dinero(p.pedido.total)}")
        if(p.pedido.nota.isNotBlank()) Text(p.pedido.nota,style=MaterialTheme.typography.bodySmall)
        acciones()
    }
}
