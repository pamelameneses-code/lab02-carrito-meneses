package com.tecsup.rickymorty

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tecsup.rickymorty.data.auth.FirebaseAuthRepository
import com.tecsup.rickymorty.ui.login.LoginViewModel
import com.tecsup.rickymorty.ui.favoritos.FavoritosViewModel
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

    private val auth = FirebaseAuthRepository(context.applicationContext)

    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer { LoginViewModel(auth) }
        initializer { ListaViewModel(items) }
        initializer { FavoritosViewModel(items) }
        initializer { DetalleViewModel(items, checkNotNull(createSavedStateHandle().get<Int>("id"))) }
    }
}
