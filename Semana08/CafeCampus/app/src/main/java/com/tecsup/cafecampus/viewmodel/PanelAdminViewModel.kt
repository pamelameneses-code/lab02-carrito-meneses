package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import java.util.Calendar
class PanelAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    private val inicio = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0)
    }.timeInMillis
    val nuevos = repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val preparando = repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val listos = repo.observarCantidadPorEstado(EstadoPedido.LISTO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val ventasHoy = repo.observarVentasHoy(inicio).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0.0)
    val cantidadHoy = combine(EstadoPedido.entries.map {repo.observarPedidosPorEstado(it)}) {grupos ->
        grupos.sumOf {lista -> lista.count {it.pedido.fecha>=inicio && it.pedido.estado!=EstadoPedido.CANCELADO.name}}
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val pedidosNuevos = repo.observarPedidosPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    fun preparar(p: PedidoConDetalle) { viewModelScope.launch {
        try { if(p.pedido.estado == EstadoPedido.RECIBIDO.name) repo.cambiarEstado(p.pedido.id,EstadoPedido.PREPARANDO) }
        catch(e: Exception) { if(e is CancellationException) throw e; _error.value = "No se pudo preparar el pedido" }
    } }
}
