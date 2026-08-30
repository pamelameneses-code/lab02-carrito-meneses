package com.meneses.pamela.calculadoracuotas

data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)

fun calcularMontoInicial(producto: Producto): Double {
    return producto.precio * producto.cantidad
}