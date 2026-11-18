package com.meneses.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.meneses.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionNoDisponible : Exception("Activa el GPS e intenta de nuevo")

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {
    private val aplicacion = context.applicationContext
    private val cliente by lazy { LocationServices.getFusedLocationProviderClient(aplicacion) }

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(aplicacion) == ConnectionResult.SUCCESS

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()
        return try {
            val posicion = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancelacion.token).await()
                ?: return Result.failure(UbicacionNoDisponible())
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
