package com.meneses.pamela.lab02carritokotlin

import java.util.Locale

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
    println("=========================================")

    val nombreCliente = "Pamela Meneses"
    val carrito = mutableListOf<Producto>()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Mecánico", 120.0, 1))
    carrito.add(Producto("Monitor 24 pulgadas", 650.0, 1))

    println("Cliente: $nombreCliente")
    println()

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
}