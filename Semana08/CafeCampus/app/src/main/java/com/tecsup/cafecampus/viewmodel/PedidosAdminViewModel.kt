package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class PedidosAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    val estadoSeleccionado = MutableStateFlow(EstadoPedido.RECIBIDO)
    val pedidos = estadoSeleccionado.flatMapLatest { repo.observarPedidosPorEstado(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val cache = mutableMapOf<Int,StateFlow<PedidoConDetalle?>>()
    fun pedido(id: Int) = cache.getOrPut(id) { repo.observarPedido(id).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),null) }
    fun siguiente(p: PedidoConDetalle): EstadoPedido? = EstadoPedido.valueOf(p.pedido.estado).siguiente()
    fun puedeCancelar(p: PedidoConDetalle) = p.pedido.estado in listOf("RECIBIDO","PREPARANDO","LISTO")
    fun avanzar(p: PedidoConDetalle) { siguiente(p)?.let { cambiar(p,it) } }
    fun cancelar(p: PedidoConDetalle) { if(puedeCancelar(p)) cambiar(p,EstadoPedido.CANCELADO) }
    fun cambiar(p: PedidoConDetalle, estado: EstadoPedido) { viewModelScope.launch {
        try {
            if(estado == siguiente(p) || (estado == EstadoPedido.CANCELADO && puedeCancelar(p)))
                repo.cambiarEstado(p.pedido.id,estado)
        } catch(e: Exception) { if(e is CancellationException) throw e; _error.value = "No se pudo cambiar el estado" }
    } }
}
