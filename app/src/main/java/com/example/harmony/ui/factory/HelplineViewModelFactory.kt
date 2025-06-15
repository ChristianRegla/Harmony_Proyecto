package com.example.harmony.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.model.HelplineModel
import com.example.harmony.ui.viewModel.HelplineViewModel

class HelplineViewModelFactory(
    private val helplineModel: HelplineModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelplineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HelplineViewModel(helplineModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}