package com.tecsup.cafecampus.data.local.entity

import androidx.room.*
import com.tecsup.cafecampus.model.EstadoPedido
@Entity(tableName = "pedidos", foreignKeys = [ForeignKey(entity = Usuario::class,
    parentColumns = ["id"], childColumns = ["clienteId"])], indices = [Index("clienteId")])
data class Pedido(@PrimaryKey(autoGenerate = true) val id: Int = 0, val clienteId: Int,
    val estado: String = EstadoPedido.RECIBIDO.name, val horaRecojo: String,
    val pago: String, val nota: String = "", val total: Double,
    val fecha: Long = System.currentTimeMillis())
