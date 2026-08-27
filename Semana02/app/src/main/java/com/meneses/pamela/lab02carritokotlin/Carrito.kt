package com.meneses.pamela.lab02carritokotlin

import java.util.Locale

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f",
            i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

// Reto: Función para buscar producto con .find
fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equalsIgnoreCase(nombre) }
}

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

    println()
    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println()
    println(String.format("Subtotal:        S/ %8.2f", subtotal))
    println(String.format("IGV (18%%):       S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR:   S/ %8.2f", total))

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }

    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    println()
    if (descuento > 0) {
        println(String.format("Descuento aplicado: S/ %8.2f", descuento))
        println(String.format("TOTAL CON DESCUENTO: S/ %8.2f", totalConDescuento))
    } else {
        println("No se aplica descuento (total no supera S/ 3000)")
    }

    // --- SE AGREGÓ ESTO AL FINAL ---
    val productoBuscado = carrito.find { it.nombre == "Mouse Logitech" }
    println("\nProducto encontrado con find: ${productoBuscado?.nombre}")

    carrito.removeIf { it.nombre == "Mouse Logitech" }
    println("Producto eliminado con removeIf. Quedan: ${carrito.size} productos.")
}