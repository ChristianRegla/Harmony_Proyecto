package com.example.harmony.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.harmony.R
import com.example.harmony.ui.main.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "harmony_channel_id"

    fun showNotification(context: Context, title: String, content: String) {
        // Creamos el intent para abrir la app cuando se toca la noti
        // Y se apunta al LoginActivity que es el que se encarga de la nav
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Si el teléfono tiene android 12 o más debemos especificar FLAG_IMMUTABLE o FLAG_MUTABLE
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentFlags)

        // Construimos la notificación
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_harmony)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Mostramos la notificación
        with(NotificationManagerCompat.from(context)) {
            // notificationId es un entero único para cada notificación.
            // Usar System.currentTimeMillis().toInt() puede ser una forma simple de asegurar unicidad.
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}