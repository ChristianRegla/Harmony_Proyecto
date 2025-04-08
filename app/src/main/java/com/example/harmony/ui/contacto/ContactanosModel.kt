package com.example.harmony.ui.contacto

import android.content.Context
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

class ContactanosModel(private val context: Context) {
    suspend fun cargarApodoEnDrawerContent(): String = withContext(Dispatchers.IO) {
        var apodo = ""

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()

        if (currentUser != null) {
            val userId = currentUser.uid

            try {
                val preferences = context.dataStore.data.first()
                val cachedNickname = preferences[stringPreferencesKey("nickname")]

                if (cachedNickname != null) {
                    apodo = cachedNickname
                } else {
                    val document = db.collection("usuarios").document(userId).get().await()

                    if (document.exists()) {
                        val nickname = document.getString("apodo")
                        apodo = nickname.toString()

                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("nickname")] = apodo
                        }
                    } else {
                        apodo = context.getString(R.string.apodo_no_encontrado)
                    }
                }
            } catch (e: Exception) {
                apodo = context.getString(R.string.error_apodo)
            }
        } else {
            apodo = context.getString(R.string.usuario_no_autenticado)
        }

        apodo
    }
}