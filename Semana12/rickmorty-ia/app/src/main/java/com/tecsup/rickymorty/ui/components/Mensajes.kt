package com.tecsup.rickymorty.ui.components

import java.io.IOException
import java.net.SocketTimeoutException
import retrofit2.HttpException

fun mensajeDeDatos(error: Throwable): String = when (error) {
    is SocketTimeoutException -> "El servidor tardó demasiado en responder. Intenta nuevamente."
    is IOException -> "No hay conexión a internet."
    is HttpException -> when (error.code()) {
        404 -> "No se encontró el recurso solicitado."
        429 -> "El servidor recibió demasiadas solicitudes. Espera un momento antes de reintentar."
        in 500..599 -> "El servicio de personajes no está disponible ahora. Intenta más tarde."
        else -> "El servidor respondió con el error ${error.code()}."
    }
    else -> "Ocurrió un error al consultar los personajes."
}
