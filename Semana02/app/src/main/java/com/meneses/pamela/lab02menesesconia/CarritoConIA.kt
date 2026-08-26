package com.meneses.pamela.lab02menesesconia

abstract class Producto(
    val nombre: String,
    val precioBase: Double,
    val cantidad: Int
) {
    init {
        require(cantidad > 0) { "La cantidad debe ser mayor a cero" }
    }

    abstract fun calcularPrecioUnitario(): Double
    abstract fun categoria(): String

    fun calcularSubtotal(): Double {
        return calcularPrecioUnitario() * cantidad
    }
}

class ProductoElectronico(nombre: String, precioBase: Double, cantidad: Int) :
    Producto(nombre, precioBase, cantidad) {
    override fun calcularPrecioUnitario(): Double = precioBase * 1.18
    override fun categoria(): String = "Electronico"
}

class ProductoAccesorio(nombre: String, precioBase: Double, cantidad: Int) :
    Producto(nombre, precioBase, cantidad) {
    override fun calcularPrecioUnitario(): Double = precioBase
    override fun categoria(): String = "Accesorio"
}

// ============================================
// NUEVA FUNCION: genera una factura con los productos seleccionados
// ============================================
fun generarFactura(cliente: String, seleccionados: List<Producto>) {
    println("\n==========================================")
    println("              FACTURA DE VENTA")
    println("==========================================")
    println("Cliente: $cliente")
    println("------------------------------------------")

    var totalFactura = 0.0
    seleccionados.forEachIndexed { index, p ->
        val subtotal = p.calcularSubtotal()
        totalFactura += subtotal
        println("${index + 1}. ${p.nombre.padEnd(20)} [${p.categoria()}] S/ ${"%8.2f".format(subtotal)}")
    }

    println("------------------------------------------")
    println("TOTAL FACTURADO: S/ ${"%8.2f".format(totalFactura)}")
    println("==========================================")
}

fun main() {
    val cliente = "Pamela Meneses"

    val carrito: List<Producto> = listOf(
        ProductoElectronico("Laptop HP", 2500.0, 1),
        ProductoAccesorio("Mouse Logitech", 45.5, 2),
        ProductoAccesorio("Teclado Mecanico", 120.0, 1),
        ProductoElectronico("Monitor 24 pulgadas", 650.0, 1)
    )

    println("==========================================")
    println("   CARRITO DE COMPRAS CON IA - TECSUP")
    println("==========================================")
    println("Cliente: $cliente\n")

    println("--------- DETALLE DEL CARRITO ---------")
    carrito.forEachIndexed { index, p ->
        println("${index + 1}. ${p.nombre.padEnd(20)} [${p.categoria()}] x${p.cantidad} S/ ${"%8.2f".format(p.calcularSubtotal())}")
    }
    println("----------------------------------------")

    val cantidadProductos = carrito.size
    val subtotal = carrito.sumOf { it.calcularSubtotal() }
    val total = subtotal

    println("Cantidad de productos: $cantidadProductos\n")
    println("Subtotal:         S/ ${"%8.2f".format(subtotal)}")
    println("TOTAL A PAGAR:    S/ ${"%8.2f".format(total)}")

    val productoMasCaro = carrito.maxByOrNull { it.calcularPrecioUnitario() }
    productoMasCaro?.let {
        println("\nProducto mas caro: ${it.nombre} (S/ ${"%.2f".format(it.calcularPrecioUnitario())})")
    }

    val porcentajeDescuento = if (total > 3000.0) 0.10 else 0.05
    val descuento = total * porcentajeDescuento
    val totalConDescuento = total - descuento

    println("Descuento aplicado (${(porcentajeDescuento * 100).toInt()}%): S/ ${"%8.2f".format(descuento)}")
    println("TOTAL CON DESCUENTO: S/ ${"%8.2f".format(totalConDescuento)}")

    println("\n--------- BUSQUEDA INTELIGENTE ---------")
    val busqueda = "Mouse Logitech"
    val encontrado = carrito.find { it.nombre.equals(busqueda, ignoreCase = true) }
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} | Categoria: ${encontrado.categoria()} | Stock: ${encontrado.cantidad}")
    } else {
        println("Producto '$busqueda' no encontrado.")
    }

    val seleccionados = carrito.filter { it.categoria() == "Electronico" }
    generarFactura(cliente, seleccionados)
}