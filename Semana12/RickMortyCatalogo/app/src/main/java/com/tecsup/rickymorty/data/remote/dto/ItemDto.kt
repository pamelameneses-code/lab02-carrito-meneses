package com.tecsup.rickymorty.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: Int,
    val name: String,
    val species: String,
    val image: String,
    val status: String,
    val origin: LugarDto
)

@Serializable
data class LugarDto(val name: String)

@Serializable
data class ListaDto(val results: List<ItemDto>)
