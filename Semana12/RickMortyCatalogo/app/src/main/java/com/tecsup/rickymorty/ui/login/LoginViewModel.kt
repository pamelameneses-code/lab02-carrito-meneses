package com.tecsup.rickymorty.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tecsup.rickymorty.data.auth.AuthRepository
import com.tecsup.rickymorty.data.repository.resultado
import com.tecsup.rickymorty.domain.model.Sesion
import com.tecsup.rickymorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val auth: AuthRepository) : ViewModel() {
    private val _estado = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
    val estado = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            _estado.value = resultado { auth.sesionActual() }.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensaje(it)) }
            )
        }
    }

    fun iniciarSesion(correo: String, clave: String) {
        if (_estado.value is UiState.Cargando) return
        if (correo.isBlank() || clave.isBlank()) {
            _estado.value = UiState.Error("Completa el correo y la contraseña.")
            return
        }
        _estado.value = UiState.Cargando
        viewModelScope.launch {
            _estado.value = auth.iniciarSesion(correo.trim(), clave).fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensaje(it)) }
            )
        }
    }

    fun cerrarSesion() {
        auth.cerrarSesion()
        _estado.value = UiState.Exito(null)
    }

    private fun mensaje(error: Throwable) = when (error) {
        is FirebaseNetworkException -> "Revisa tu conexión e intenta de nuevo."
        is FirebaseFirestoreException -> "No se pudo consultar tu rol en Firestore."
        is IllegalStateException -> error.message ?: "No se pudo iniciar sesión."
        else -> "No se pudo iniciar sesión. Intenta de nuevo."
    }
}
