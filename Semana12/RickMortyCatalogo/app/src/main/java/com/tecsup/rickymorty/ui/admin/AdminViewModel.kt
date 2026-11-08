package com.tecsup.rickymorty.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.data.repository.resultado
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.notification.Notificador
import com.tecsup.rickymorty.ui.components.mensajeDeDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TotalesAdmin(val guardados: Int, val favoritos: Int)

class AdminViewModel(private val repo: ItemRepository, private val notificador: Notificador) : ViewModel() {
    val estado = repo.items.map { items ->
        val totales: UiState<TotalesAdmin> = UiState.Exito(TotalesAdmin(items.size, items.count { it.esFavorito }))
        totales
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)

    private val _ocupado = MutableStateFlow(false)
    val ocupado = _ocupado.asStateFlow()
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    fun sincronizar() = ejecutar {
        repo.refrescar().map { n ->
            val texto = "Se guardaron $n elementos"
            notificador.mostrar("Datos actualizados", texto)
            texto
        }
    }

    fun limpiarCache() = ejecutar {
        resultado {
            repo.limpiarCache()
            "Caché eliminada."
        }
    }

    private fun ejecutar(operacion: suspend () -> Result<String>) {
        if (_ocupado.value) return
        _ocupado.value = true
        _mensaje.value = null
        viewModelScope.launch {
            try {
                _mensaje.value = operacion().fold(
                    onSuccess = { it },
                    onFailure = { mensajeDeDatos(it) }
                )
            } finally {
                _ocupado.value = false
            }
        }
    }
}
