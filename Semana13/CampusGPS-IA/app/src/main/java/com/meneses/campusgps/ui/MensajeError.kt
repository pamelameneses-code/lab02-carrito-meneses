package com.meneses.campusgps.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.meneses.campusgps.model.TipoError
import com.meneses.campusgps.model.UbicacionUi

@Composable
fun MensajeError(error: UbicacionUi.Error, reintentar: () -> Unit) {
    val contexto = LocalContext.current
    val colores = MaterialTheme.colorScheme
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = colores.errorContainer)) {
        Column(Modifier.padding(12.dp)) {
            Text(error.mensaje, color = colores.onErrorContainer)
            when (error.tipo) {
                TipoError.SIN_PERMISO -> TextButton(onClick = {
                    contexto.startActivity(Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", contexto.packageName, null)
                    ))
                }) { Text("Abrir ajustes") }
                TipoError.GPS_APAGADO, TipoError.OTRO -> TextButton(onClick = reintentar) { Text("Reintentar") }
                TipoError.SIN_PLAY_SERVICES -> Unit
            }
        }
    }
}
