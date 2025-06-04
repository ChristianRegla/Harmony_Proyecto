package com.example.harmony.ui.home

import com.example.harmony.ui.home.HomeViewModel.EmotionDetails
import java.time.DayOfWeek

data class DailyEmotionSummary (
    val dayOfWeek: DayOfWeek,
    val dayAbbreviation: String,
    val mostFrequentEmotion: EmotionDetails?
)

data class MonthlyEmotionDataPoint(
    val dayOfMonth: Int,
    val averageEmotionValue: Float // El valor numérico promedio del día
)

data class EmotionEntry(
    val emotionId: Long = 0L,
    val activityId: Long = 0L, // Aunque no la usemos directamente en el resumen
    val timestamp: String = "" // "yyyy-MM-dd HH:mm"
)