package com.example.harmony.ui.registeremotions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterEmotionsViewModelFactory(
    private val registerEmotionsModel: RegisterEmotionsModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterEmotionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterEmotionsViewModel(registerEmotionsModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}