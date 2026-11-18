package com.meneses.campusgps.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
    val categoria by vm.categoria.collectAsState()
    val radio by vm.radio.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val cercano by vm.masCercano.collectAsState()
    val contexto = LocalContext.current
    val permisos = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    fun permisoConcedido(): Boolean = permisos.any {
        ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
    }
    var mostrarUbicacion by remember { mutableStateOf(permisoConcedido()) }
    val propietario = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(propietario, contexto) {
        val observador = androidx.lifecycle.LifecycleEventObserver { _, evento ->
            if (evento == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                mostrarUbicacion = permisoConcedido()
            }
        }
        propietario.lifecycle.addObserver(observador)
        onDispose { propietario.lifecycle.removeObserver(observador) }
    }

    val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultado ->
        mostrarUbicacion = resultado.values.any { it } || permisoConcedido()
        if (mostrarUbicacion) vm.buscar() else vm.permisoDenegado()
    }
    val buscar: () -> Unit = {
        mostrarUbicacion = permisoConcedido()
        if (mostrarUbicacion) {
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
            Row(
                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                (listOf(null) + vm.categorias).forEach { opcion ->
                    FilterChip(
                        selected = categoria == opcion,
                        onClick = { vm.filtrarCategoria(opcion) },
                        label = { Text(opcion ?: "Todos") }
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                vm.radiosDisponibles.forEach { metros ->
                    FilterChip(
                        selected = radio == metros,
                        onClick = { vm.cambiarRadio(metros) },
                        label = { Text("Radio: ${metros} m") }
                    )
                }
            }
            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                modifier = Modifier.fillMaxWidth().weight(1f),
                idMasCercano = actual?.id,
                radioMetros = radio,
                mostrarUbicacion = mostrarUbicacion
            )
            ListaLugares(lugares, seleccionado, radio, vm::seleccionar)
        }
    }
}
