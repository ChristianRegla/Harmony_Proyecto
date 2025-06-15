package com.example.harmony.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.model.RelaxModel
import com.example.harmony.ui.viewModel.RelaxViewModel

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