package com.example.harmony.ui.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class MessageModel(
    var message: String = "", // Valor por defecto y var para mutabilidad
    var role: String = "",    // Valor por defecto y var para mutabilidad
    var timestamp: Long = System.currentTimeMillis(), // Valor por defecto
    @Exclude var id: String = "" // Excluido de Firestore
) {
    // Constructor sin argumentos requerido por Firestore
    constructor() : this("", "", 0)

    fun toMap(): Map<String, Any> {
        return mapOf(
            "message" to message,
            "role" to role,
            "timestamp" to timestamp
        )
    }
}