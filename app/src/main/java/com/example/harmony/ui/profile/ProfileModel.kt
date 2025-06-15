package com.example.harmony.ui.profile

import android.content.Context
import com.example.harmony.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileModel(private val context: Context) {

    suspend fun cargarApodoEnDrawerContent(context: Context, db: FirebaseFirestore): String  {
        val currentUser = FirebaseAuth.getInstance().currentUser

        return if (currentUser != null) {
            val userId = currentUser.uid

            try {
                val document = db.collection("usuarios").document(userId).get().await()
                if (document.exists()) {
                    val nickname = document.getString("apodo")
                    nickname ?: context.getString(R.string.apodo_no_encontrado)
                } else {
                    context.getString(R.string.apodo_no_encontrado)
                }
            } catch (e: Exception) {
                context.getString(R.string.error_apodo)
            }
        } else {
            context.getString(R.string.usuario_no_autenticado)
        }
    }
    suspend fun cargarImagenUrl(): String? = withContext(Dispatchers.IO) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()

        return@withContext try {
            val document = db.collection("usuarios").document(currentUser?.uid ?: "").get().await()
            document.getString("imagenes")
        } catch (e: Exception) {
            null
        }
    }

}

data class PerfilModel(
    val userID: String = "",
    val nombre: String = "",
    val apodo: String = "",
    val email: String = "",
    val profileImageUrl: String? = null
)