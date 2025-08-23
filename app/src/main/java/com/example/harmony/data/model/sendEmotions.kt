package com.example.harmony.data.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

// Clase de datos para almacenar un registro en Firestore
data class EmotionEntry(
    val timestamp: String,
    val emotionId: Int,
    val activityId: Int
)

// Función para enviar el registro a Firestore
fun sendEmotions(selectedEmotionIndex: Int, selectedActivityIndex: Int, selectedDate: String, selectedTime: String) {
    val db = FirebaseFirestore.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    if (uid != null) {
        val timestamp = "$selectedDate $selectedTime" // Usa la fecha y hora seleccionadas

        val emotionEntry = EmotionEntry(timestamp, selectedEmotionIndex, selectedActivityIndex)

        db.collection("usuarios").document(uid).collection("emociones").add(emotionEntry)
            .addOnSuccessListener {
                Log.d("Firestore", "¡Registro guardado exitosamente con fecha y hora seleccionadas!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al guardar registro: ${e.message}")
            }
    } else {
        Log.e("Firestore", "Error: No hay usuario autenticado")
    }
}