package com.example.harmony.ui.ejercicios

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EjerciciosViewModelFactory(
    private val ejerciciosModel: EjerciciosModel,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EjerciciosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjerciciosViewModel(ejerciciosModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}