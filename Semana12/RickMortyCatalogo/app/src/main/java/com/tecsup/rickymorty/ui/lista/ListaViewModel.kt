package com.tecsup.rickymorty.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.mensajeDeDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaViewModel(private val repo: ItemRepository) : ViewModel() {
    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado = _estado.asStateFlow()

    init { refrescar() }

    fun refrescar() {
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            repo.refrescar().fold(
                onSuccess = { repo.items.collect { _estado.value = UiState.Exito(it) } },
                onFailure = { _estado.value = UiState.Error(mensajeDeDatos(it)) }
            )
        }
    }
}
