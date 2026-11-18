package com.meneses.campusgps.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.meneses.campusgps.model.UbicacionUi
import com.meneses.campusgps.util.formatearDistancia
import com.meneses.campusgps.viewmodel.MapaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val cercano by vm.masCercano.collectAsState()
    val contexto = LocalContext.current
    val permisos = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }
    val buscar: () -> Unit = {
        if (permisos.any { ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED }) {
            vm.buscar()
        } else {
            lanzador.launch(permisos)
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("CampusGPS") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = buscar, enabled = estado != UbicacionUi.Buscando) {
                Text("Obtener mi ubicación")
            }
            when (val actual = estado) {
                UbicacionUi.Inicial -> Unit
                UbicacionUi.Buscando -> CircularProgressIndicator()
                is UbicacionUi.Lista -> Text("Latitud: ${actual.posicion.latitud} · Longitud: ${actual.posicion.longitud}")
                is UbicacionUi.Error -> MensajeError(actual, buscar)
            }
            val actual = cercano
            OutlinedButton(onClick = { actual?.let { vm.seleccionar(it.id) } }, enabled = actual != null) {
                Text("Ir al más cercano")
            }
            if (actual != null) {
                lugares.firstOrNull { it.lugar.id == actual.id }?.metros?.let { distancia ->
                    Text("Más cercano: ${actual.nombre} (${formatearDistancia(distancia)})")
                }
            }
            MapaUbicacion(estado, lugares, seleccionado, Modifier.fillMaxWidth().weight(1f), actual?.id)
            ListaLugares(lugares, seleccionado, vm::seleccionar)
        }
    }
}
