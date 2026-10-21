package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MenuViewModel(private val repo: CafeRepository) : ViewModel() {
    val productos = repo.observarProductos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito = _carrito.asStateFlow()
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()
    fun buscar(texto: String) { _busqueda.value = texto }
    private fun normalizar(texto: String): String = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
        .filterNot { Character.getType(it) == Character.NON_SPACING_MARK.toInt() }
        .lowercase(java.util.Locale.ROOT)
    private val _soloDisponibles = MutableStateFlow(false)
    val soloDisponibles = _soloDisponibles.asStateFlow()
    fun filtrarDisponibles(valor: Boolean) { _soloDisponibles.value = valor }
    fun limpiarFiltros() { _busqueda.value = ""; categoria.value = "Todos"; _soloDisponibles.value = false }
    val categoria = MutableStateFlow("Todos")
    val categorias = listOf("Todos" to "Todo", "Desayunos" to "Desayunos", "Almuerzos" to "Almuerzos", "Bebidas" to "Bebidas")
    val filtrados = combine(productos,categoria,busqueda,soloDisponibles) { ps,c,texto,disponibles ->
        val palabras = normalizar(texto.trim()).split(Regex("\\s+")).filter {it.isNotBlank()}
        ps.filter { p ->
            val coincideCategoria = c == "Todos" || p.categoria.equals(c,true) ||
                (c == "Desayunos" && p.categoria.equals("Snacks",true)) ||
                (c == "Almuerzos" && p.categoria.equals("Menús",true))
            val contenido = normalizar("${p.nombre} ${p.descripcion} ${p.categoria}")
            coincideCategoria && (!disponibles || p.disponible) && palabras.all {contenido.contains(it)}
        }.sortedWith(compareBy<Producto> { when(it.icono) { "plato" -> 0; "pan" -> 1; "cafe" -> 2; "jugo" -> 3; else -> 4 } }.thenBy {it.nombre})
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun agregar(producto: Producto, cantidad: Int, nota: String) {
        val actual = productos.value.find { it.id == producto.id } ?: return
        if (!actual.disponible || cantidad < 1) return
        _carrito.update { lista ->
            if(lista.any { it.producto.id == actual.id }) lista.map {
                if(it.producto.id == actual.id) it.copy(producto=actual,cantidad=it.cantidad+cantidad,
                    nota=listOf(it.nota,nota).filter(String::isNotBlank).joinToString("; ")) else it
            } else lista + ItemCarrito(actual,cantidad,nota)
        }
    }
    fun quitar(productoId: Int) { _carrito.update { it.filterNot { i -> i.producto.id == productoId } } }
    fun vaciar() { _carrito.value = emptyList() }
    fun total(): Double = _carrito.value.sumOf { it.subtotal }
}
