package com.example.harmony.data.model

data class HealthMetrics(
    val heartRate: Int? = null,
    val stressLevel: Float? = null,
    val sleepQuality: Float? = null,
    val stepCount: Int? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val source: String = "smartwatch"
)

data class HealthContext(
    val currentMetrics: HealthMetrics,
    val avarageMetrics: HealthMetrics? = null,
    val emotionalState: String? = null
)