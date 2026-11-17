package com.tecsup.rickymorty.data.remote

import com.tecsup.rickymorty.data.remote.dto.ItemDto
import com.tecsup.rickymorty.data.remote.dto.ListaDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character?status=alive")
    suspend fun listar(): ListaDto

    @GET("character/{id}")
    suspend fun detalle(@Path("id") id: Int): ItemDto
}
