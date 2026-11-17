package com.meneses.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meneses.campusgps.data.LugaresRepository
import com.meneses.campusgps.data.UbicacionRepository
import com.meneses.campusgps.model.LugarConDistancia
import com.meneses.campusgps.model.UbicacionUi
import com.meneses.campusgps.model.Posicion
import com.meneses.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado = _estado.asStateFlow()
    private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
    val lugares = _lugares.asStateFlow()
    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado = _seleccionado.asStateFlow()

    fun buscar() {
        if (_estado.value == UbicacionUi.Buscando) return
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    actualizarDistancias(it)
                    _estado.value = UbicacionUi.Lista(it)
                }
                .onFailure {
                    _estado.value = UbicacionUi.Error(it.message ?: "No se pudo obtener la ubicación")
                }
        }
    }

    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(posicion, it)) }
            .sortedBy { it.metros }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás")
    }
}
