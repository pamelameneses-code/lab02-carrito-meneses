package com.meneses.centrotareas.data

import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow

import android.util.Log
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TareasRepositoryLocal : TareasRepository {
    override fun progresoTarea() = flow {
        Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
        for (paso in 1..100) {
            delay(40)
            emit(paso / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override fun contarPrimos(hasta: Int): Flow<AvancePrimos> = flow {
        require(hasta >= 2)
        val contexto = currentCoroutineContext()
        var cantidad = 0
        var ultimoPorcentaje = 0
        for (n in 2..hasta) {
            contexto.ensureActive()
            if (esPrimo(n)) cantidad++
            val porcentaje = ((n - 1L) * 100 / (hasta - 1)).toInt()
            if (porcentaje != ultimoPorcentaje) {
                emit(AvancePrimos.Calculando(porcentaje))
                ultimoPorcentaje = porcentaje
            }
        }
        emit(AvancePrimos.Terminado(cantidad))
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    private fun esPrimo(n: Int): Boolean {
        var divisor = 2
        while (divisor.toLong() * divisor <= n) {
            if (n % divisor == 0) return false
            divisor++
        }
        return true
    }
}
