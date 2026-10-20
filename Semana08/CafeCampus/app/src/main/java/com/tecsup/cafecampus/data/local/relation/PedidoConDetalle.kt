package com.tecsup.cafecampus.data.local.relation

import androidx.room.*
import com.tecsup.cafecampus.data.local.entity.*
data class PedidoConDetalle(
    @Embedded val pedido: Pedido,
    @Relation(parentColumn = "id", entityColumn = "pedidoId") val detalles: List<DetallePedido>,
    @Relation(parentColumn = "clienteId", entityColumn = "id") val cliente: Usuario
)
