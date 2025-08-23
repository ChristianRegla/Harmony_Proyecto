package com.example.harmony.data.model

import com.example.harmony.ui.viewModel.HomeViewModel.EmotionDetails
import java.time.DayOfWeek

data class DailyEmotionSummary (
    val dayOfWeek: DayOfWeek,
    val dayAbbreviation: String,
    val mostFrequentEmotion: EmotionDetails?
)

data class MonthlyEmotionDataPoint(
    val dayOfMonth: Int,
    val averageEmotionValue: Float
)

data class EmotionEntries(
    val emotionId: Long = 0L,
    val activityId: Long = 0L,
    val timestamp: String = ""
)