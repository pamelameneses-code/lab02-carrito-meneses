package com.tecsup.rickymorty.domain.model

data class Item(
    val id: Int,
    val titulo: String,
    val subtitulo: String,
    val imagenUrl: String? = null,
    val esFavorito: Boolean = false,
    val estado: String = "",
    val origen: String = ""
)
