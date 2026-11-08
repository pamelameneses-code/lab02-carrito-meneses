package com.tecsup.rickymorty.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.notification.Notificador
import com.tecsup.rickymorty.ui.components.mensajeDeDatos
import java.io.IOException
import java.text.Normalizer
import java.util.Locale
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListaViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador,
    private val savedState: SavedStateHandle = SavedStateHandle()
) : ViewModel() {
    val busqueda = savedState.getStateFlow("busqueda", "")

    fun buscar(texto: String) { savedState["busqueda"] = texto }

    fun alternarFavorito(id: Int) {
        viewModelScope.launch { repo.alternarFavorito(id) }
    }

    private val actualizacion = MutableStateFlow<UiState<Unit>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = combine(repo.items, actualizacion) { items, carga ->
        when {
            items.isNotEmpty() -> UiState.Exito(items)
            carga is UiState.Exito -> UiState.Exito(emptyList())
            carga is UiState.Error -> carga
            else -> UiState.Cargando
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Cargando)

    val resultados: StateFlow<UiState<List<Item>>> = combine(estado, busqueda) { actual, texto ->
        val consulta = normalizar(texto.trim())
        if (actual is UiState.Exito && consulta.isNotEmpty()) {
            UiState.Exito(actual.datos.filter { consulta in normalizar(it.titulo) })
        } else actual
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
                onSuccess = { n ->
                    notificador.mostrar("Datos actualizados", "Se guardaron $n elementos")
                    UiState.Exito(Unit)
                },
                onFailure = {
                    val mensaje = if (it is IOException) "Sin conexión."
                    else mensajeDeDatos(it)
                    UiState.Error(mensaje)
                }
            )
        }
    }
}

private val marcasDiacriticas = Regex("\\p{M}+")
private fun normalizar(texto: String): String =
    Normalizer.normalize(texto, Normalizer.Form.NFD)
        .replace(marcasDiacriticas, "")
        .lowercase(Locale.ROOT)
