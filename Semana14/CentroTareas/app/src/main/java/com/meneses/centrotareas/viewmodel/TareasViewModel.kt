package com.meneses.centrotareas.viewmodel

import androidx.lifecycle.ViewModel
import android.util.Log
import kotlinx.coroutines.Job
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TareasViewModel : ViewModel() {
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
                withContext(Dispatchers.Default) {
                    Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
                    for (paso in 1..100) {
                        delay(40)
                        _progreso.value = paso / 100f
                    }
                }
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
}
