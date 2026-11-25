package com.meneses.avisoscampus.notificacion

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import com.meneses.avisoscampus.MainActivity
import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.meneses.avisoscampus.R
import com.meneses.avisoscampus.model.Aviso
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object Notificador {
    const val CANAL = "avisos"
    private const val CANAL_URGENTES = "urgentes"

    fun tienePermiso(context: Context): Boolean =
        Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        if (!tienePermiso(context)) return false
        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val canal = if (aviso.urgente) CANAL_URGENTES else CANAL
        val notificacion = NotificationCompat.Builder(context, canal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setStyle(NotificationCompat.BigTextStyle().bigText(aviso.texto))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendiente)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
        return true
    }

    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(CANAL, "Avisos", NotificationManager.IMPORTANCE_DEFAULT)
            val urgente = NotificationChannel(CANAL_URGENTES, "Urgentes", NotificationManager.IMPORTANCE_HIGH)
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannels(listOf(canal, urgente))
        }
    }
}
