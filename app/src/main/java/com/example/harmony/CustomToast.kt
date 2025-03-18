package com.example.harmony

import android.content.Context
import android.widget.ImageView
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

object CustomToast {

    // Función para mostrar un Toast de alerta personalizado
    fun showAlertWithIcon(context: Context, message: String, iconResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_alert_toast, null)

        val text = layout.findViewById<TextView>(R.id.toast_text)
        text.text = message

        val icon = layout.findViewById<ImageView>(R.id.toast_icon) // Asegúrate de que esto sea un ImageView
        icon.setImageResource(iconResId) // Establece el recurso de la imagen

        val toast = Toast(context)
        toast.duration = duration
        toast.view = layout
        toast.show()
    }

    // Función para mostrar un Toast de información personalizado
    fun showInfoWithIcon(context: Context, message: String, iconResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_info_toast, null)

        val text = layout.findViewById<TextView>(R.id.toast_info_text)
        text.text = message

        val icon = layout.findViewById<ImageView>(R.id.toast_info_icon) // Asegúrate de que esto sea un ImageView
        icon.setImageResource(iconResId) // Establece el recurso de la imagen

        val toast = Toast(context)
        toast.duration = duration
        toast.view = layout
        toast.show()
    }

    // Función para mostrar un Toast de bienvenida personalizado
    fun showWelcomeWithIcon(context: Context, message: String, iconResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_welcome_toast, null)

        val text = layout.findViewById<TextView>(R.id.toast_welcome_text)
        text.text = message

        val icon = layout.findViewById<ImageView>(R.id.toast_welcome_icon) // Asegúrate de que esto sea un ImageView
        icon.setImageResource(iconResId) // Establece el recurso de la imagen

        val toast = Toast(context)
        toast.duration = duration
        toast.view = layout
        toast.show()
    }
}