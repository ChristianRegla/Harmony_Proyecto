package com.example.harmony.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun login(email: String, password: String): Result<Pair<String, String>> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("Usuario no encontrado"))

            val document = db.collection("usuarios").document(user.uid).get().await()
            if (document.exists()) {
                val apodo = document.getString("apodo") ?: ""
                Result.success(Pair(user.uid, apodo))
            } else {
                Result.failure(Exception("No se encontró el apodo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
