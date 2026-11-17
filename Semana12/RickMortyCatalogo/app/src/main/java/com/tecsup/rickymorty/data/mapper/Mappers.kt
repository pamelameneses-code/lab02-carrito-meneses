package com.tecsup.rickymorty.data.mapper

import com.tecsup.rickymorty.data.local.ItemEntity
import com.tecsup.rickymorty.data.remote.dto.ItemDto
import com.tecsup.rickymorty.domain.model.Item

fun ItemDto.aEntity(esFavorito: Boolean = false) =
    ItemEntity(id, name, species, image, status, origin.name, esFavorito)

fun ItemEntity.aModelo() =
    Item(id, titulo, subtitulo, imagenUrl, esFavorito, estado, origen)
