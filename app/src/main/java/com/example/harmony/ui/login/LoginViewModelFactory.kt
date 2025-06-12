package com.example.harmony.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.data.repository.UserPreferencesRepository

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            val authRepository = AuthRepository()
            val userPreferencesRepository = UserPreferencesRepository(context.applicationContext)

            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}