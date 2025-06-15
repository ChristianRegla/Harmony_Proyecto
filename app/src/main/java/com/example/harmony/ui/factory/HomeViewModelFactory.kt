package com.example.harmony.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.viewModel.HomeViewModel
import com.example.harmony.ui.model.HomeModel

class HomeViewModelFactory(
    private val homeModel: HomeModel,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(homeModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}