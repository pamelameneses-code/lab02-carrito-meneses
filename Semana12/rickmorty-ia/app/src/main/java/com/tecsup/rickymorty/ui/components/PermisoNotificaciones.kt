package com.tecsup.rickymorty.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun PermisoNotificaciones(contenido: @Composable () -> Unit) {
    if (Build.VERSION.SDK_INT < 33) {
        contenido()
        return
    }

    val context = LocalContext.current
    val preferencias = context.getSharedPreferences("permisos", Context.MODE_PRIVATE)
    var resuelto by rememberSaveable {
        mutableStateOf(
            preferencias.getBoolean("notificaciones_solicitadas", false) ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var solicitado by rememberSaveable { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        preferencias.edit().putBoolean("notificaciones_solicitadas", true).apply()
        resuelto = true
    }

    LaunchedEffect(Unit) {
        if (!resuelto && !solicitado) {
            solicitado = true
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
    if (resuelto) contenido() else Cargando()
}
