package com.example.harmony.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.model.ProfileModel
import com.example.harmony.ui.viewModel.ProfileViewModel

class ProfileViewModelFactory(
    private val profileModel: ProfileModel,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}