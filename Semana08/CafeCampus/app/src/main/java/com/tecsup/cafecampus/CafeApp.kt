package com.tecsup.cafecampus

import android.app.Application
import com.tecsup.cafecampus.data.local.*
import com.tecsup.cafecampus.data.repository.*
import com.tecsup.cafecampus.data.prefs.PreferenciasStore
import kotlinx.coroutines.*
class CafeApp : Application() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val database by lazy { CafeDatabase.obtener(this) }
    val preferencias by lazy { PreferenciasStore(this) }
    val repository: CafeRepository by lazy { CafeRepositoryRoom(database) }
    lateinit var inicializacion: Deferred<Unit>
        private set
    override fun onCreate() {
        super.onCreate()
        inicializacion = scope.async { DatosIniciales.sembrar(database) }
    }
}
