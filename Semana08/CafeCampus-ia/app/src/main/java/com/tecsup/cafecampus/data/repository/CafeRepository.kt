package com.tecsup.cafecampus.data.repository

import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.flow.Flow
interface CafeRepository {
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun usuario(id: Int): Usuario?
    suspend fun usuarioPorRol(rol: Rol): Usuario?
    fun observarProductos(): Flow<List<Producto>>
    suspend fun importarFoto(origen: String): String
    suspend fun guardarProducto(p: Producto)
    suspend fun eliminarProducto(p: Producto)
    suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean)
    suspend fun crearPedido(clienteId: Int, items: List<ItemCarrito>, horaRecojo: String, pago: String, nota: String): Long
    fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido)
    fun observarPedidosPorHora(inicioDia: Long, finDia: Long): Flow<List<PedidosPorHora>>
    fun observarVentasHoy(inicioDia: Long): Flow<Double>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
}
