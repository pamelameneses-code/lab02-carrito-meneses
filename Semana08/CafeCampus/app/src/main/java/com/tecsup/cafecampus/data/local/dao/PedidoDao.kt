package com.tecsup.cafecampus.data.local.dao

import androidx.room.*
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import kotlinx.coroutines.flow.Flow
@Dao
abstract class PedidoDao {
    @Insert abstract suspend fun insertarPedido(p: Pedido): Long
    @Insert abstract suspend fun insertarDetalles(lista: List<DetallePedido>)
    @Query("UPDATE pedidos SET estado = :estado WHERE id = :id")
    abstract suspend fun cambiarEstado(id: Int, estado: String)
    @Transaction open suspend fun crearPedido(pedido: Pedido, detalles: List<DetallePedido>): Long {
        val id = insertarPedido(pedido)
        insertarDetalles(detalles.map { it.copy(pedidoId = id.toInt()) })
        return id
    }
    @Transaction @Query("SELECT * FROM pedidos WHERE clienteId = :clienteId ORDER BY fecha DESC")
    abstract fun observarPorCliente(clienteId: Int): Flow<List<PedidoConDetalle>>
    @Transaction @Query("SELECT * FROM pedidos WHERE estado = :estado ORDER BY fecha DESC")
    abstract fun observarPorEstado(estado: String): Flow<List<PedidoConDetalle>>
    @Transaction @Query("SELECT * FROM pedidos WHERE id = :id ORDER BY fecha DESC")
    abstract fun observarUno(id: Int): Flow<PedidoConDetalle?>
    @Query("SELECT COALESCE(SUM(total), 0.0) FROM pedidos WHERE fecha >= :inicio AND estado != 'CANCELADO'")
    abstract fun ventasDesde(inicio: Long): Flow<Double>
    @Query("SELECT COUNT(*) FROM pedidos WHERE estado = :estado")
    abstract fun cantidadPorEstado(estado: String): Flow<Int>
}
