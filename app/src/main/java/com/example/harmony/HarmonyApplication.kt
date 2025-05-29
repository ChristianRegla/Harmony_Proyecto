package com.example.harmony

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.content.Context
import com.example.harmony.utils.AlarmScheduler

class HarmonyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)

        AlarmScheduler.scheduleDailyReminder(applicationContext, 20, 0)
    }

    private fun createNotificationChannel(context: Context) {
        // Creamos el canal solo para API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "harmony_channel_id"
            val channelName = "Harmony Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Canal para notificaciones generales de Harmony"
            }
            // Registramos al canal con el sistema
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}