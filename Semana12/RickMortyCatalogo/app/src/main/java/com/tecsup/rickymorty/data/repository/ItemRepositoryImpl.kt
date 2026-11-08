package com.tecsup.rickymorty.data.repository

import androidx.room.withTransaction
import com.tecsup.rickymorty.data.local.AppDatabase
import com.tecsup.rickymorty.data.mapper.aEntity
import com.tecsup.rickymorty.data.mapper.aModelo
import com.tecsup.rickymorty.data.remote.ApiService
import com.tecsup.rickymorty.domain.model.Item
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ItemRepositoryImpl(private val api: ApiService, private val db: AppDatabase) : ItemRepository {
    private val dao = db.itemDao()
    private val sincronizacion = Mutex()

    override val items = dao.todos().map { lista -> lista.map { it.aModelo() } }
    override val favoritos = dao.favoritos().map { lista -> lista.map { it.aModelo() } }

    override suspend fun refrescar() = resultado {
        sincronizacion.withLock {
            val nuevos = api.listar().results
            db.withTransaction {
                val favoritos = dao.idsFavoritos().toSet()
                dao.guardarTodos(nuevos.map { it.aEntity(it.id in favoritos) })
            }
            nuevos.size
        }
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> = resultado {
        val dto = api.detalle(id)
        dto.aEntity(dao.buscar(id)?.esFavorito == true).aModelo()
    }

    override suspend fun alternarFavorito(id: Int) = dao.alternarFavorito(id)
    override suspend fun cantidadGuardada() = dao.contar()
    override suspend fun cantidadFavoritos() = dao.contarFavoritos()
    override suspend fun limpiarCache() = sincronizacion.withLock { dao.borrarTodo() }
}

internal suspend fun <T> resultado(operacion: suspend () -> T): Result<T> = try {
    Result.success(operacion())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.failure(e)
}
