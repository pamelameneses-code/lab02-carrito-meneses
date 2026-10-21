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
@OptIn(ExperimentalCoroutinesApi::class)
class PanelAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    private fun ventanaDia(): Pair<Long,Long> {
        val calendario=Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY,0);set(Calendar.MINUTE,0);set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0)
        }
        val inicio=calendario.timeInMillis
        calendario.add(Calendar.DAY_OF_YEAR,1)
        return inicio to calendario.timeInMillis
    }
    private val dia = flow {
        while(currentCoroutineContext().isActive) {
            val ventana=ventanaDia();emit(ventana)
            delay((ventana.second-System.currentTimeMillis()).coerceAtLeast(1000L))
        }
    }.stateIn(viewModelScope,SharingStarted.Eagerly,ventanaDia())
    val horaActual: Int get() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val pedidosPorHora = dia.flatMapLatest { (inicio,fin) -> repo.observarPedidosPorHora(inicio,fin) }
        .map {datos -> val porHora=datos.associateBy {it.hora};List(24) {h -> PedidosPorHora(h,porHora[h]?.cantidad ?: 0)} }
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),List(24) {PedidosPorHora(it,0)})
    val nuevos = repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val preparando = repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val listos = repo.observarCantidadPorEstado(EstadoPedido.LISTO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val ventasHoy = dia.flatMapLatest {repo.observarVentasHoy(it.first)}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0.0)
    val cantidadHoy = dia.flatMapLatest { (inicio,fin) ->
        combine(EstadoPedido.entries.map {repo.observarPedidosPorEstado(it)}) {grupos ->
            grupos.sumOf {lista -> lista.count {it.pedido.fecha>=inicio && it.pedido.fecha<fin && it.pedido.estado!=EstadoPedido.CANCELADO.name}}
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    val pedidosNuevos = repo.observarPedidosPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    fun preparar(p: PedidoConDetalle) { viewModelScope.launch {
        try { if(p.pedido.estado == EstadoPedido.RECIBIDO.name) repo.cambiarEstado(p.pedido.id,EstadoPedido.PREPARANDO) }
        catch(e: Exception) { if(e is CancellationException) throw e; _error.value = "No se pudo preparar el pedido" }
    } }
}
