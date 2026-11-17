package com.meneses.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.meneses.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {
    private val cliente = LocationServices.getFusedLocationProviderClient(context.applicationContext)

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()
        return try {
            val posicion = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancelacion.token).await()
                ?: return Result.failure(IllegalStateException("Activa el GPS e intenta de nuevo"))
            Result.success(Posicion(posicion.latitude, posicion.longitude))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(error)
        } finally {
            cancelacion.cancel()
        }
    }
}
