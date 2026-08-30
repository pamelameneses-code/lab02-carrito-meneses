package com.meneses.pamela.calculadoracuotas

data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)

fun calcularMontoInicial(producto: Producto): Double {
    return producto.precio * producto.cantidad
}

fun validarCuotas(cuotas: Int): Boolean {
    return cuotas == 6 || cuotas == 12 || cuotas == 24
}

fun obtenerTasaInteres(cuotas: Int): Double {
    return when (cuotas) {
        6 -> 0.20
        12 -> 0.40
        24 -> 0.60
        else -> 0.0
    }
}
