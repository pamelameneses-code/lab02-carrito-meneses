package com.meneses.campusgps.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.meneses.campusgps.data.LugaresRepository
import com.meneses.campusgps.data.UbicacionRepository

fun campusViewModelFactory(
    ubicacion: UbicacionRepository,
    lugares: LugaresRepository
): ViewModelProvider.Factory = viewModelFactory {
    initializer { MapaViewModel(ubicacion, lugares) }
}
