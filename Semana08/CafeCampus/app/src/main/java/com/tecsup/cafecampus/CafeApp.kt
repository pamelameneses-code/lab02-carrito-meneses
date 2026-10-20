package com.tecsup.cafecampus

import android.app.Application
import com.tecsup.cafecampus.data.local.*
import kotlinx.coroutines.*
class CafeApp : Application() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val database by lazy { CafeDatabase.obtener(this) }
    lateinit var inicializacion: Deferred<Unit>
        private set
    override fun onCreate() {
        super.onCreate()
        inicializacion = scope.async { DatosIniciales.sembrar(database) }
    }
}
