package com.meneses.centrotareas.viewmodel

import androidx.lifecycle.ViewModel
import java.io.IOException
import android.util.Log
import kotlinx.coroutines.Job
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.meneses.centrotareas.data.AvancePrimos
import com.meneses.centrotareas.data.TareasRepository
import com.meneses.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import com.meneses.centrotareas.model.CargaUi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso = _progreso.asStateFlow()

    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return
        trabajo = viewModelScope.launch {
            _corriendo.value = true
            _progreso.value = 0f
            _mensaje.value = "Trabajando..."
            try {
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.progresoTarea().collect { _progreso.value = it }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        if (!_corriendo.value) return
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos = _primos.asStateFlow()
    private var calculo: Job? = null

    fun contar(hasta: Int) {
        if (hasta < 2 || calculo?.isActive == true) return
        calculo = viewModelScope.launch {
            _primos.value = PrimosUi.Calculando(0)
            val inicio = System.currentTimeMillis()
            try {
                repo.contarPrimos(hasta).collect { avance ->
                    _primos.value = when (avance) {
                        is AvancePrimos.Calculando -> PrimosUi.Calculando(avance.porcentaje)
                        is AvancePrimos.Terminado -> PrimosUi.Listo(
                            avance.cantidad, System.currentTimeMillis() - inicio
                        )
                    }
                }
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        calculo?.cancel()
    }

    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga = _carga.asStateFlow()

    private val _simularError = MutableStateFlow(false)
    val simularError = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    fun cargarUnaPorUna() = cargar(enParalelo = false)

    fun cargarALaVez() = cargar(enParalelo = true)

    private fun cargar(enParalelo: Boolean) {
        if (_carga.value == CargaUi.Cargando) return
        val fallaSimulada = _simularError.value
        viewModelScope.launch {
            _carga.value = CargaUi.Cargando
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = if (enParalelo) {
                    coroutineScope {
                        val perfilPendiente = async { repo.cargarPerfil() }
                        val pedidosPendientes = async { repo.cargarPedidos(fallaSimulada) }
                        perfilPendiente.await() to pedidosPendientes.await()
                    }
                } else {
                    repo.cargarPerfil() to repo.cargarPedidos(fallaSimulada)
                }
                val modo = if (enParalelo) "A la vez" else "Una por una"
                _carga.value = CargaUi.Exito(modo, perfil, pedidos.size, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _carga.value = CargaUi.Error("No se pudo cargar: ${e.message}")
            }
        }
    }
}
