package com.meneses.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meneses.murocampus.data.PostRepository
import com.meneses.murocampus.data.mensajeDe
import com.meneses.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()
    private var cargaLista: Job? = null

    init {
        cargar()
    }

    fun cargar() {
        cargaLista?.cancel()
        cargaLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            _lista.value = repo.listar().fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) },
            )
        }
    }
}
