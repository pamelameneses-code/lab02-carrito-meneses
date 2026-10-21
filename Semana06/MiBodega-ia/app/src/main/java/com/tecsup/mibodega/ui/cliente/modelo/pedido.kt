package com.tecsup.mibodega.ui.cliente.modelo

data class Pedido(
    val numero: Int,
    val cantidadProductos: Int,
    val total: Double,
    val direccion: String,
    val metodoPago: String,
    val estado: String = "En preparación"
)