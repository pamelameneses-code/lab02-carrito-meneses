package com.tecsup.cafecampus.data.repository

import androidx.room.withTransaction
import com.tecsup.cafecampus.data.local.CafeDatabase
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.flow.first
class CafeRepositoryRoom(private val db: CafeDatabase) : CafeRepository {
    override suspend fun login(correo: String, clave: String) = db.usuarioDao().login(correo, clave)
    override suspend fun usuario(id: Int) = db.usuarioDao().buscar(id)
    override suspend fun usuarioPorRol(rol: Rol) = db.usuarioDao().porRol(rol.name)
    override fun observarProductos() = db.productoDao().observarTodos()
    override suspend fun guardarProducto(p: Producto) {
        if (p.id == 0) db.productoDao().insertar(p) else db.productoDao().actualizar(p)
    }
    override suspend fun eliminarProducto(p: Producto) = db.productoDao().eliminar(p)
    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) = db.productoDao().cambiarDisponibilidad(id, disponible)
    override suspend fun crearPedido(clienteId: Int, items: List<ItemCarrito>, horaRecojo: String, pago: String, nota: String): Long = db.withTransaction {
        require(items.isNotEmpty()) { "El carrito está vacío" }
        val actuales = db.productoDao().observarTodos().first().associateBy { it.id }
        val detalles = items.map { item ->
            val p = actuales[item.producto.id]
            require(p != null && p.disponible) { "Un producto ya no está disponible" }
            require(item.cantidad > 0) { "Cantidad inválida" }
            DetallePedido(productoId=p.id, nombreProducto=p.nombre, cantidad=item.cantidad, precioUnitario=p.precio)
        }
        val notas = listOf(nota.trim()) + items.filter { it.nota.isNotBlank() }.map { "${it.producto.nombre}: ${it.nota}" }
        db.pedidoDao().crearPedido(Pedido(clienteId=clienteId, horaRecojo=horaRecojo,
            pago=pago, nota=notas.filter { it.isNotBlank() }.joinToString("\n"),
            total=detalles.sumOf { it.cantidad * it.precioUnitario }), detalles)
    }
    override fun observarPedidosCliente(clienteId: Int) = db.pedidoDao().observarPorCliente(clienteId)
    override fun observarPedidosPorEstado(estado: EstadoPedido) = db.pedidoDao().observarPorEstado(estado.name)
    override fun observarPedido(id: Int) = db.pedidoDao().observarUno(id)
    override suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido) = db.pedidoDao().cambiarEstado(pedidoId, estado.name)
    override fun observarPedidosPorHora(inicioDia: Long, finDia: Long) = db.pedidoDao().pedidosPorHora(inicioDia,finDia)
    override fun observarVentasHoy(inicioDia: Long) = db.pedidoDao().ventasDesde(inicioDia)
    override fun observarCantidadPorEstado(estado: EstadoPedido) = db.pedidoDao().cantidadPorEstado(estado.name)
}
