package com.meneses.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meneses.campusgps.data.LugaresRepositoryLocal
import com.meneses.campusgps.data.UbicacionRepositoryGoogle
import com.meneses.campusgps.ui.PantallaMapa
import com.meneses.campusgps.ui.theme.CampusGPSTheme
import com.meneses.campusgps.viewmodel.MapaViewModel
import com.meneses.campusgps.viewmodel.campusViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fabrica = campusViewModelFactory(UbicacionRepositoryGoogle(this), LugaresRepositoryLocal())
        setContent {
            CampusGPSTheme {
                val vm: MapaViewModel = viewModel(factory = fabrica)
                PantallaMapa(vm)
            }
        }
    }
}
