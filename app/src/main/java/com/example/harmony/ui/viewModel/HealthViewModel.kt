package com.example.harmony.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.data.model.HealthMetrics
import com.example.harmony.data.repository.HealthDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HealthViewModel(
    private val repository: HealthDataRepository,
    context: Context
) : ViewModel() {

    private val _healthMetrics = MutableStateFlow<HealthMetrics?>(null)
    val healthMetrics: StateFlow<HealthMetrics?> = _healthMetrics

    fun fetchLatestMetrics() {
        viewModelScope.launch {
            _healthMetrics.value = repository.getLatestHealthMetrics()
        }
    }
}