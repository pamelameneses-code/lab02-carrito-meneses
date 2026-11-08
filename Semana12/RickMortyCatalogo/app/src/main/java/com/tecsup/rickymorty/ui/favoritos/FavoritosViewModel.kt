package com.tecsup.rickymorty.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.ui.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritosViewModel(repo: ItemRepository) : ViewModel() {
    val estado = repo.favoritos.map { UiState.Exito(it) as UiState<List<com.tecsup.rickymorty.domain.model.Item>> }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)
}
