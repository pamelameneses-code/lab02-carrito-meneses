package com.tecsup.rickymorty

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.rickymorty.data.remote.RetrofitClient
import com.tecsup.rickymorty.data.repository.ItemRepositoryImpl
import com.tecsup.rickymorty.ui.lista.ListaViewModel

class AppContainer {
    private val items = ItemRepositoryImpl(RetrofitClient.api)

    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer { ListaViewModel(items) }
    }
}
