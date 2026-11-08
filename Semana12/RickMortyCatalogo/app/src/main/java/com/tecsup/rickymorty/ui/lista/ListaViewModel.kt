package com.tecsup.rickymorty.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.mensajeDeDatos
import java.io.IOException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListaViewModel(private val repo: ItemRepository) : ViewModel() {
    private val actualizacion = MutableStateFlow<UiState<Unit>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = combine(repo.items, actualizacion) { items, carga ->
        when {
            items.isNotEmpty() -> UiState.Exito(items)
            carga is UiState.Exito -> UiState.Exito(emptyList())
            carga is UiState.Error -> carga
            else -> UiState.Cargando
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Cargando)

    val aviso = combine(repo.items, actualizacion) { items, carga ->
        (carga as? UiState.Error)?.mensaje?.takeIf { items.isNotEmpty() }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private var consulta: Job? = null

    init { refrescar() }

    fun refrescar() {
        if (consulta?.isActive == true) return
        consulta = viewModelScope.launch {
            actualizacion.value = UiState.Cargando
            actualizacion.value = repo.refrescar().fold(
                onSuccess = { UiState.Exito(Unit) },
                onFailure = {
                    val mensaje = if (it is IOException) "Sin conexión."
                    else mensajeDeDatos(it)
                    UiState.Error(mensaje)
                }
            )
        }
    }
}
