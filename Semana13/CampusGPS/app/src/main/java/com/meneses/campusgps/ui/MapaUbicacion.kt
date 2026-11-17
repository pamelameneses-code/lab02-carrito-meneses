package com.meneses.campusgps.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.meneses.campusgps.model.LugarConDistancia
import com.meneses.campusgps.model.UbicacionUi

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }
    val posicion = (estado as? UbicacionUi.Lista)?.posicion
    LaunchedEffect(estado) {
        if (posicion != null) {
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(posicion.latitud, posicion.longitud), 16f))
        }
    }
    GoogleMap(modifier = modifier, cameraPositionState = camara) {
        if (posicion != null) {
            Marker(
                state = rememberUpdatedMarkerState(position = LatLng(posicion.latitud, posicion.longitud)),
                title = "Estás aquí"
            )
        }
    }
}
