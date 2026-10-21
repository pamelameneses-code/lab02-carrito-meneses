package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MenuAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    val productos = repo.observarProductos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val categoria = MutableStateFlow("Todos")
    val categorias = listOf("Todos" to "Todo", "Desayunos" to "Desayunos", "Almuerzos" to "Almuerzos", "Bebidas" to "Bebidas")
    fun etiquetaCategoria(categoria: String): String = when(categoria) {"Snacks" -> "Desayunos"; "Menús" -> "Almuerzos"; else -> categoria}
    val filtrados = combine(productos,categoria) { ps,c ->
        ps.filter {c=="Todos" || etiquetaCategoria(it.categoria).equals(c,true)}
            .sortedWith(compareBy<Producto> {when(it.icono) {"plato" -> 0; "pan" -> 1; "cafe" -> 2; else -> 3}}.thenBy {it.nombre})
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _guardando = MutableStateFlow(false)
    val guardando = _guardando.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _guardado = MutableStateFlow(false)
    val guardado = _guardado.asStateFlow()
    fun consumirGuardado() { _guardado.value = false; _error.value = null }
    fun guardar(producto: Producto) {
        if(producto.nombre.isBlank() || !producto.precio.isFinite() || producto.precio <= 0) {
            _error.value = "Nombre obligatorio y precio mayor que 0"; return
        }
        if(_guardando.value) return
        _guardando.value = true
        ejecutar {
            try { repo.guardarProducto(producto.copy(nombre=producto.nombre.trim())); _guardado.value = true }
            finally { _guardando.value = false }
        }
    }
    fun eliminar(producto: Producto) = ejecutar { repo.eliminarProducto(producto) }
    fun alternarDisponibilidad(producto: Producto) = ejecutar { repo.cambiarDisponibilidad(producto.id,!producto.disponible) }
    private fun ejecutar(accion: suspend () -> Unit) { viewModelScope.launch {
        try { _error.value = null; accion() }
        catch(e: Exception) { if(e is CancellationException) throw e; _error.value = "No se pudo guardar el cambio" }
    } }
}
