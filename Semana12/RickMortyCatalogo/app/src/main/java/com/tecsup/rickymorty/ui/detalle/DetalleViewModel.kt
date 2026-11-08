package com.tecsup.rickymorty.ui.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.mensajeDeDatos
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetalleViewModel(private val repo: ItemRepository, private val id: Int) : ViewModel() {
    private val _estado = MutableStateFlow<UiState<Item>>(UiState.Cargando)
    val estado = _estado.asStateFlow()
    private var consulta: Job? = null

    init { cargar() }

    fun alternarFavorito() {
        if (_estado.value !is UiState.Exito) return
        viewModelScope.launch {
            repo.alternarFavorito(id)
            val marcado = repo.favoritos.first().any { it.id == id }
            val actual = _estado.value as? UiState.Exito ?: return@launch
            _estado.value = UiState.Exito(actual.datos.copy(esFavorito = marcado))
        }
    }

    fun cargar() {
        if (consulta?.isActive == true) return
        consulta = viewModelScope.launch {
            _estado.value = UiState.Cargando
            _estado.value = repo.obtenerDetalle(id).fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDeDatos(it)) }
            )
        }
    }
}
