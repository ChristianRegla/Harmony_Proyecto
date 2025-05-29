package com.example.harmony.ui.helpline

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.harmony.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HelplineModel(private val context: Context) {

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

}