package com.example.harmony.ui.relax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context

class RelaxViewModelFactory(
    private val relaxModel: RelaxModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RelaxViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RelaxViewModel(relaxModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}