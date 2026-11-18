package com.meneses.campusgps.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
    modifier: Modifier = Modifier,
    idMasCercano: Int? = null,
    radioMetros: Int,
    mostrarUbicacion: Boolean
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
    LaunchedEffect(seleccionado) {
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
        if (lugar != null) {
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(lugar.latitud, lugar.longitud), 17f))
        }
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara,
        properties = MapProperties(isMyLocationEnabled = mostrarUbicacion),
        uiSettings = MapUiSettings(myLocationButtonEnabled = mostrarUbicacion)
    ) {
        lugares.forEach { entrada ->
            val lugar = entrada.lugar
            key(lugar.id) {
                Marker(
                    state = rememberUpdatedMarkerState(position = LatLng(lugar.latitud, lugar.longitud)),
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (lugar.id == idMasCercano) BitmapDescriptorFactory.HUE_GREEN
                        else BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            }
        }
        if (posicion != null) {
            Circle(
                center = LatLng(posicion.latitud, posicion.longitud),
                radius = radioMetros.toDouble(),
                strokeColor = Color(0xFF1976D2),
                strokeWidth = 3f,
                fillColor = Color(0x261976D2)
            )
            Marker(
                state = rememberUpdatedMarkerState(position = LatLng(posicion.latitud, posicion.longitud)),
                title = "Estás aquí"
            )
        }
    }
}
