package com.tecsup.rickymorty.data.repository

import com.tecsup.rickymorty.data.mapper.aModelo
import com.tecsup.rickymorty.data.remote.ApiService
import com.tecsup.rickymorty.domain.model.Item
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class ItemRepositoryImpl(private val api: ApiService) : ItemRepository {
    override val items = MutableStateFlow(emptyList<Item>())
    override val favoritos = flowOf(emptyList<Item>())

    override suspend fun refrescar() = resultado {
        val nuevos = api.listar().results.map { it.aModelo() }
        items.value = nuevos
        nuevos.size
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> =
        resultado { api.detalle(id).aModelo() }

    override suspend fun alternarFavorito(id: Int) = Unit
    override suspend fun cantidadGuardada() = items.value.size
    override suspend fun cantidadFavoritos() = 0
    override suspend fun limpiarCache() { items.value = emptyList() }
}

internal suspend fun <T> resultado(operacion: suspend () -> T): Result<T> = try {
    Result.success(operacion())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.failure(e)
}
