package com.meneses.centrotareas.data

import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    fun progresoTarea(): Flow<Float>
    fun contarPrimos(hasta: Int): Flow<AvancePrimos>
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}

sealed interface AvancePrimos {
    data class Calculando(val porcentaje: Int) : AvancePrimos
    data class Terminado(val cantidad: Int) : AvancePrimos
}
