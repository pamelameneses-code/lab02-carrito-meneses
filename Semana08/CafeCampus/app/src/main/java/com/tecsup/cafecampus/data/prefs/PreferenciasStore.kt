package com.tecsup.cafecampus.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.tecsup.cafecampus.model.Rol
import kotlinx.coroutines.flow.*
import java.io.IOException
val Context.dataStore by preferencesDataStore("ajustes")
class PreferenciasStore(context: Context) {
    private val store = context.applicationContext.dataStore
    private val perfil = stringPreferencesKey("perfilActivo")
    private val oscuro = booleanPreferencesKey("modoOscuro")
    private val recibe = booleanPreferencesKey("recibirPedidos")
    private val avisos = booleanPreferencesKey("notificaciones")
    private val usuario = intPreferencesKey("usuarioId")
    private val datos = store.data.catch { if (it is IOException) emit(emptyPreferences()) else throw it }
    val perfilActivo: Flow<String> = datos.map { it[perfil] ?: "" }
    val modoOscuro: Flow<Boolean> = datos.map { it[oscuro] ?: false }
    val recibirPedidos: Flow<Boolean> = datos.map { it[recibe] ?: true }
    val notificaciones: Flow<Boolean> = datos.map {it[avisos] ?: true}
    val usuarioId: Flow<Int> = datos.map { it[usuario] ?: 0 }
    suspend fun guardarNotificaciones(b: Boolean) {store.edit {it[avisos] = b}}
    suspend fun guardarPerfil(rol: Rol) { store.edit { it[perfil] = rol.name } }
    suspend fun guardarSesion(rol: Rol, id: Int) { store.edit { it[perfil] = rol.name; it[usuario] = id } }
    suspend fun guardarModoOscuro(b: Boolean) { store.edit { it[oscuro] = b } }
    suspend fun guardarRecibirPedidos(b: Boolean) { store.edit { it[recibe] = b } }
    suspend fun cerrarSesion() { store.edit { it.remove(perfil); it.remove(usuario) } }
}
