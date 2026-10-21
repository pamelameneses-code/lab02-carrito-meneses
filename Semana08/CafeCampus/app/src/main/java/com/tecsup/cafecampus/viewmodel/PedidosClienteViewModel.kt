package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import com.tecsup.cafecampus.data.prefs.PreferenciasStore
class PedidosClienteViewModel(private val repo: CafeRepository, private val clienteId: Int,
    private val prefs: PreferenciasStore) : ViewModel() {
    val misPedidos = repo.observarPedidosCliente(clienteId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val recibirPedidos = prefs.recibirPedidos.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    private val _creado = MutableStateFlow<Long?>(null)
    val creado = _creado.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _enviando = MutableStateFlow(false)
    val enviando = _enviando.asStateFlow()
    val mostrarActivos = MutableStateFlow(true)
    val visibles = combine(misPedidos,mostrarActivos) { pedidos,activos ->
        pedidos.filter { (it.pedido.estado in listOf("RECIBIDO","PREPARANDO","LISTO")) == activos }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val cache = mutableMapOf<Int, StateFlow<PedidoConDetalle?>>()
    fun pedido(id: Int): StateFlow<PedidoConDetalle?> = cache.getOrPut(id) {
        repo.observarPedido(id).map { it?.takeIf { p -> p.pedido.clienteId == clienteId } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }
    fun horasRecojo(): List<String> {
        val ahora = java.util.Calendar.getInstance()
        val minutos = ahora.get(java.util.Calendar.MINUTE)
        ahora.add(java.util.Calendar.MINUTE,30 - minutos % 30)
        val formato = java.text.SimpleDateFormat("HH:mm",java.util.Locale.forLanguageTag("es-PE"))
        return listOf("Ahora") + List(3) {
            val hora = formato.format(ahora.time)
            ahora.add(java.util.Calendar.MINUTE,30)
            hora
        }
    }
    fun resumen(p: PedidoConDetalle): String {
        val primero=p.detalles.firstOrNull() ?: return "Sin productos"
        return "${primero.nombreProducto} ×${primero.cantidad}" + if(p.detalles.size>1) " + ${p.detalles.size-1} más" else ""
    }
    fun consumirCreado() { _creado.value = null }
    fun confirmar(items: List<ItemCarrito>, horaRecojo: String, pago: String, nota: String) {
        if (_enviando.value) return
        _enviando.value = true
        viewModelScope.launch {
            try {
                _error.value = null
                require(prefs.recibirPedidos.first()) { "Cafetería cerrada" }
                require(horaRecojo.isNotBlank() && pago in listOf("CAJA","YAPE")) { "Revisa hora y pago" }
                _creado.value = repo.crearPedido(clienteId,items,horaRecojo,pago,nota)
            } catch(e: Exception) { if(e is CancellationException) throw e; _error.value = e.message ?: "No se pudo confirmar" }
            finally { _enviando.value = false }
        }
    }
}
