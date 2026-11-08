package com.tecsup.rickymorty.ui.lista

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

class ListaViewModel(private val repo: ItemRepository) : ViewModel() {
    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado = _estado.asStateFlow()
    private var consulta: Job? = null

    init {
        viewModelScope.launch {
            repo.items.collect { lista ->
                if (_estado.value is UiState.Exito) _estado.value = UiState.Exito(lista)
            }
        }
        refrescar()
    }

    fun refrescar() {
        if (consulta?.isActive == true) return
        consulta = viewModelScope.launch {
            _estado.value = UiState.Cargando
            repo.refrescar().fold(
                onSuccess = { _estado.value = UiState.Exito(repo.items.first()) },
                onFailure = { _estado.value = UiState.Error(mensajeDeDatos(it)) }
            )
        }
    }
}
