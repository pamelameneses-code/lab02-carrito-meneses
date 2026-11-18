package com.meneses.centrotareas.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.meneses.centrotareas.data.TareasRepositoryLocal

val TareasViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    initializer { TareasViewModel(TareasRepositoryLocal()) }
}
