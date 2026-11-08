package com.tecsup.rickymorty.data.mapper

import com.tecsup.rickymorty.data.remote.dto.ItemDto
import com.tecsup.rickymorty.domain.model.Item

fun ItemDto.aModelo() = Item(id, name, species, image, estado = status, origen = origin.name)
