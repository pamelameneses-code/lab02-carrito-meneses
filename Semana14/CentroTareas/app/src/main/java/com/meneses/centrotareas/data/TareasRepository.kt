package com.meneses.centrotareas.data

interface TareasRepository {
    suspend fun contarPrimos(hasta: Int): Int
}
