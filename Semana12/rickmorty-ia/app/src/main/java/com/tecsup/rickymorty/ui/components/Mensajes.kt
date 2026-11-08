package com.tecsup.rickymorty.ui.components

import java.io.IOException
import retrofit2.HttpException

fun mensajeDeDatos(error: Throwable): String = when (error) {
    is IOException -> "No hay conexión a internet."
    is HttpException -> "El servidor respondió con el error ${error.code()}."
    else -> "Ocurrió un error al consultar los personajes."
}
