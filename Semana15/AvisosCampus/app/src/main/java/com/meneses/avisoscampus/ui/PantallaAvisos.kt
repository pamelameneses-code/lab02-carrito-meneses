package com.meneses.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.meneses.avisoscampus.receiver.ModoAvionReceiver
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import com.meneses.avisoscampus.model.PermisoUi
import com.meneses.avisoscampus.notificacion.Notificador
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.meneses.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            if (!Notificador.mostrar(contexto, aviso)) vm.noSePudoMostrar()
        }
    }
    val escuchando by vm.escuchandoAvion.collectAsState()
    DisposableEffect(escuchando) {
        if (escuchando) {
            val receptor = ModoAvionReceiver(vm::alCambiarModoAvion)
            ContextCompat.registerReceiver(
                contexto, receptor, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }
    val permiso by vm.permiso.collectAsState()
    val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        vm.permisoRespondido(it)
    }
    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("AvisosCampus") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }) { padding ->
        Column(
            modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Tarjeta("Permiso de notificaciones") {
                val (etiqueta, color) = when (permiso) {
                    PermisoUi.SinPedir -> "Sin pedir" to Color.Gray
                    PermisoUi.Concedido -> "Permitido" to Color(0xFF2E7D32)
                    PermisoUi.Denegado -> "Denegado" to MaterialTheme.colorScheme.error
                }
                Text(etiqueta, color = color)
                Button(onClick = {
                    if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
                    else if (Build.VERSION.SDK_INT >= 33) {
                        lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }) { Text("Permitir notificaciones") }
            }
            val mensaje by vm.mensaje.collectAsState()
            mensaje?.let { error ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(error, color = MaterialTheme.colorScheme.onErrorContainer)
                        OutlinedButton(onClick = {
                            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                            contexto.startActivity(intent)
                        }) { Text("Abrir ajustes") }
                    }
                }
            }
            Tarjeta("Avisos") {
                Button(onClick = vm::avisarAhora) { Text("Mostrar aviso") }
            }
            val faltan by vm.faltan.collectAsState()
            Tarjeta("Recordatorio") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { vm.recordarEn() }, enabled = faltan == null) {
                        Text("Recordar en 10 s")
                    }
                    OutlinedButton(onClick = vm::cancelarRecordatorio, enabled = faltan != null) {
                        Text("Cancelar")
                    }
                }
                faltan?.let { Text("Faltan ${it}s") }
            }
            Tarjeta("Receptor de anuncios") {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Escuchar el modo avión", modifier = Modifier.weight(1f))
                    Switch(checked = escuchando, onCheckedChange = vm::cambiarEscucha)
                }
            }
            val avisos by vm.avisos.collectAsState()
            HistorialAvisos(avisos)
        }
    }
}

@Composable
internal fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            contenido()
        }
    }
}
