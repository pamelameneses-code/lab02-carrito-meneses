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

fun calcularInteres(montoInicial: Double, tasa: Double): Double {
    return montoInicial * tasa
}

fun calcularMontoAPagar(montoInicial: Double, interes: Double): Double {
    return montoInicial + interes
}

fun calcularPagoMensual(montoAPagar: Double, cuotas: Int): Double {
    return montoAPagar / cuotas
}

fun mostrarCronograma(montoAPagar: Double, pagoMensual: Double, cuotas: Int) {
    println("--------- CRONOGRAMA DE PAGOS ---------")
    println(String.format("%-4s %-12s %10s %10s %12s",
        "N°", "Fecha", "Monto", "P.Mensual", "Resta Pago"))

    var saldo = montoAPagar
    var mes = 9
    var anio = 2026

    for (i in 1..cuotas) {
        val montoAntes = saldo
        saldo -= pagoMensual
        mes++
        if (mes > 12) { mes = 1; anio++ }
        val fecha = String.format("26/%02d/%d", mes, anio)
        println(String.format("%-4d %-12s %10.2f %10.2f %12.2f",
            i, fecha, montoAntes, pagoMensual, saldo))
    }
    println("----------------------------------------")
}

fun main() {
    println("=========================================")
    println("   CALCULADORA DE CUOTAS - TIENDA TECSUP  ")
    println("=========================================")

    print("Ingrese nombre del producto: ")
    val nombre = readLine() ?: ""
    print("Ingrese precio: ")
    val precio = readLine()?.toDoubleOrNull() ?: 0.0
    print("Ingrese cantidad: ")
    val cantidad = readLine()?.toIntOrNull() ?: 1

    val producto = Producto(nombre, precio, cantidad)
    val montoInicial = calcularMontoInicial(producto)

    var cuotas: Int
    do {
        print("Ingrese N° de cuotas (6, 12 o 24): ")
        cuotas = readLine()?.toIntOrNull() ?: 0
        if (!validarCuotas(cuotas)) println("Cuotas inválidas. Solo 6, 12 o 24.")
    } while (!validarCuotas(cuotas))

    val tasa = obtenerTasaInteres(cuotas)
    val interes = calcularInteres(montoInicial, tasa)
    val montoAPagar = calcularMontoAPagar(montoInicial, interes)
    val pagoMensual = calcularPagoMensual(montoAPagar, cuotas)

    println()
    println("Producto: ${producto.nombre}")
    println(String.format("Monto inicial:   S/ %10.2f", montoInicial))
    println(String.format("Interés (%.0f%%):    S/ %10.2f", tasa * 100, interes))
    println(String.format("Monto a pagar:   S/ %10.2f", montoAPagar))
    println(String.format("Pago mensual:    S/ %10.2f", pagoMensual))

    mostrarCronograma(montoAPagar, pagoMensual, cuotas)
}