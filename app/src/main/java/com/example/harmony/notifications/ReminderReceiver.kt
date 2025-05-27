package com.example.harmony.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.harmony.R
import com.example.harmony.ui.login.LoginActivity
import android.Manifest
import android.content.pm.PackageManager

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "harmony_channel_id"
        const val DESTINATION_ROUTE = "destination_route_key"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Si el permiso no está otorgado, no se puede mostrar la notificación.
                // Desde un BroadcastReceiver no se puede solicitar el permiso directamente.
                // La app debería haberlo solicitado y obtenido antes.
                // Puedes registrar un log o simplemente no hacer nada.
                println("Permiso POST_NOTIFICATIONS no otorgado. No se puede mostrar la notificación programada.")
                return
            }
        }

        // Crear el intent para la actividad de destino
        val tapIntent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(DESTINATION_ROUTE, "registerEmotions")
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            pendingIntentFlags
        )

        val tituloRecordatorio = context.getString(R.string.app_name)
        val contenidoRecordatorio = context.getString(R.string.lleva_un_registro)


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_harmony)
            .setContentTitle(tituloRecordatorio)
            .setContentText(contenidoRecordatorio)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // Ahora esta llamada está precedida por la verificación de permisos para API 33+
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}