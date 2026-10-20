package com.tecsup.cafecampus.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.repository.CafeRepository
import kotlinx.coroutines.flow.*
class MenuViewModel(private val repo: CafeRepository) : ViewModel() {
    val productos = repo.observarProductos().stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
}
