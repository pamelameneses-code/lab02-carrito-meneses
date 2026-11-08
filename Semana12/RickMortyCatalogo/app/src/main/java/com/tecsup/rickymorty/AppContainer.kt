package com.tecsup.rickymorty

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.tecsup.rickymorty.data.local.AppDatabase
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.createSavedStateHandle
import com.tecsup.rickymorty.ui.detalle.DetalleViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.rickymorty.data.remote.RetrofitClient
import com.tecsup.rickymorty.data.repository.ItemRepositoryImpl
import com.tecsup.rickymorty.ui.lista.ListaViewModel

class AppContainer(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext, AppDatabase::class.java, "rick-morty.db"
    ).build()
    private val items = ItemRepositoryImpl(RetrofitClient.api, db)

    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer { ListaViewModel(items) }
        initializer { DetalleViewModel(items, checkNotNull(createSavedStateHandle().get<Int>("id"))) }
    }
}
