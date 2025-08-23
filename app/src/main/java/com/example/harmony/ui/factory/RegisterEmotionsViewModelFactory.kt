package com.example.harmony.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.model.RegisterEmotionsModel
import com.example.harmony.ui.viewModel.RegisterEmotionsViewModel

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