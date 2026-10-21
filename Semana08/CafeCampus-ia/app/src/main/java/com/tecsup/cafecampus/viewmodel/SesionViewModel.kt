package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import com.tecsup.cafecampus.data.prefs.PreferenciasStore
class SesionViewModel(private val repo: CafeRepository, private val prefs: PreferenciasStore,
    private val inicializacion: Deferred<Unit>) : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual = _usuarioActual.asStateFlow()
    private val _rolLogin = MutableStateFlow(Rol.CLIENTE)
    val rolLogin = _rolLogin.asStateFlow()
    fun seleccionarRol(rol: Rol) { _rolLogin.value = rol; _error.value = null }
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _cargando = MutableStateFlow(true)
    val cargando = _cargando.asStateFlow()
    val modoOscuro = prefs.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val notificaciones = prefs.notificaciones.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val recibirPedidos = prefs.recibirPedidos.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    init { viewModelScope.launch {
        try {
            inicializacion.await()
            // Una nueva ejecución exige credenciales; el tema y las otras preferencias se conservan.
            prefs.cerrarSesion()
        } catch (e: Exception) { if (e is CancellationException) throw e; _error.value = "No se pudo iniciar la aplicación: ${e.message}" }
        finally { _cargando.value = false }
    } }
    fun login(correo: String, clave: String, rolSeleccionado: Rol) {
        if (_cargando.value) return
        if (correo.isBlank() || clave.isBlank()) { _error.value = "Completa todos los campos"; return }
        viewModelScope.launch {
            _cargando.value = true; _error.value = null
            try {
                val u = repo.login(correo.trim(), clave)
                when {
                    u == null -> _error.value = "Correo o contraseña incorrectos"
                    u.rol != rolSeleccionado.name -> _error.value = "El usuario no corresponde al perfil seleccionado"
                    else -> { prefs.guardarSesion(rolSeleccionado, u.id); _usuarioActual.value = u }
                }
            } catch(e: Exception) { if(e is CancellationException) throw e; _error.value = "No se pudo iniciar sesión" }
            finally { _cargando.value = false }
        }
    }
    fun cambiarPerfil() {
        val destino = if (_usuarioActual.value?.rol == Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
        salirAlLogin(destino)
    }
    fun cerrarSesion() { salirAlLogin(Rol.CLIENTE) }
    private fun salirAlLogin(rol: Rol) {
        _rolLogin.value = rol
        _error.value = null
        _usuarioActual.value = null
        viewModelScope.launch {
            try { prefs.cerrarSesion() }
            catch(e: Exception) {
                if(e is CancellationException) throw e
                _error.value = "No se pudo limpiar la preferencia de sesión"
            }
        }
    }
    fun guardarNotificaciones(b: Boolean) {viewModelScope.launch {prefs.guardarNotificaciones(b)}}
    fun guardarModoOscuro(b: Boolean) { viewModelScope.launch { prefs.guardarModoOscuro(b) } }
    fun guardarRecibirPedidos(b: Boolean) { viewModelScope.launch { prefs.guardarRecibirPedidos(b) } }
}
