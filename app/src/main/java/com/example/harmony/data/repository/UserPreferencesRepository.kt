package com.example.harmony.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dataStore

class UserPreferencesRepository(private val context: Context) {

    // Centraliza las claves de tus preferencias
    private object PreferencesKeys {
        val NICKNAME = stringPreferencesKey("nickname")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveUserSession(nickname: String, email: String?) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NICKNAME] = nickname
            if (email != null) {
                preferences[PreferencesKeys.EMAIL] = email
            }
        }
    }
}