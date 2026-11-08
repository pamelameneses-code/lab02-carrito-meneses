package com.tecsup.rickymorty.data.auth

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.rickymorty.data.repository.resultado
import com.tecsup.rickymorty.domain.model.Rol
import com.tecsup.rickymorty.domain.model.Sesion
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(private val context: Context) : AuthRepository {
    private fun app() = FirebaseApp.getApps(context).firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
        ?: error("Configura Firebase con app/google-services.json.")

    override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> = resultado {
        val auth = FirebaseAuth.getInstance(app())
        val usuario = try {
            auth.signInWithEmailAndPassword(correo, clave).await().user
                ?: error("Firebase no devolvió una sesión.")
        } catch (e: FirebaseAuthException) {
            val mensaje = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Revisa el formato del correo."
                "ERROR_WRONG_PASSWORD", "ERROR_USER_NOT_FOUND", "ERROR_INVALID_CREDENTIAL",
                "ERROR_INVALID_LOGIN_CREDENTIALS" -> "Correo o contraseña incorrectos."
                "ERROR_USER_DISABLED" -> "Esta cuenta está deshabilitada."
                else -> "No se pudo iniciar sesión. Intenta de nuevo."
            }
            throw IllegalStateException(mensaje, e)
        }
        val sesion = resultado { leerSesion(usuario) }
        if (sesion.isFailure) auth.signOut()
        sesion.getOrThrow()
    }

    override suspend fun sesionActual(): Sesion? =
        FirebaseAuth.getInstance(app()).currentUser?.let { leerSesion(it) }

    override fun cerrarSesion() = FirebaseAuth.getInstance(app()).signOut()

    private suspend fun leerSesion(usuario: FirebaseUser): Sesion {
        val perfil = FirebaseFirestore.getInstance(app())
            .collection("usuarios").document(usuario.uid).get().await()
        val rol = when (perfil.getString("rol")) {
            "admin" -> Rol.ADMIN
            "usuario" -> Rol.USUARIO
            else -> error("Esta cuenta no tiene un rol válido en Firestore.")
        }
        return Sesion(usuario.uid, usuario.email.orEmpty(), rol)
    }
}
