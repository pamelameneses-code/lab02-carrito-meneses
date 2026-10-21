package com.tecsup.cafecampus.data.local.entity

import androidx.room.*
// productoId no es FK: el historial sobrevive a la eliminación del producto.
@Entity(tableName = "detalle_pedido", foreignKeys = [ForeignKey(entity = Pedido::class,
    parentColumns = ["id"], childColumns = ["pedidoId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("pedidoId")])
data class DetallePedido(@PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pedidoId: Int = 0, val productoId: Int, val nombreProducto: String,
    val cantidad: Int, val precioUnitario: Double)
