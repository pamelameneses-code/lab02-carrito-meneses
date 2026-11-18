package com.meneses.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meneses.campusgps.data.LugaresRepository
import com.meneses.campusgps.data.UbicacionRepository
import com.meneses.campusgps.data.UbicacionNoDisponible
import com.meneses.campusgps.model.TipoError
import com.meneses.campusgps.model.LugarConDistancia
import com.meneses.campusgps.model.UbicacionUi
import com.meneses.campusgps.model.Posicion
import com.meneses.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.meneses.campusgps.model.Lugar
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado = _estado.asStateFlow()
    private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
    val lugares = _lugares.asStateFlow()
    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado = _seleccionado.asStateFlow()

    fun buscar() {
        if (_estado.value == UbicacionUi.Buscando) return
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error("Este dispositivo no tiene Google Play Services", TipoError.SIN_PLAY_SERVICES)
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    actualizarDistancias(it)
                    _estado.value = UbicacionUi.Lista(it)
                }
                .onFailure {
                    when (it) {
                        is SecurityException -> permisoDenegado()
                        is UbicacionNoDisponible -> {
                            _estado.value = UbicacionUi.Error("Activa el GPS e intenta de nuevo", TipoError.GPS_APAGADO)
                        }
                        else -> _estado.value = UbicacionUi.Error("No se pudo obtener la ubicación", TipoError.OTRO)
                    }
                }
        }
    }

    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(posicion, it)) }
            .sortedBy { it.metros }
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás",
            TipoError.SIN_PERMISO
        )
    }
}
