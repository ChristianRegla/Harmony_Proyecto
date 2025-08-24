package com.example.harmony.data.repository

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.harmony.data.model.HealthMetrics
import java.time.LocalDateTime

class HealthDataRepository(private val context: Context) {
    suspend fun getLatestHealthMetrics(): HealthMetrics? {
        return try {
            val healthConnectClient = HealthConnectClient.getOrCreate(context)

            val heartRateRequest = ReadRecordsRequest(
                recordType = HeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    LocalDateTime.now().minusHours(1),
                    LocalDateTime.now()
                )
            )
            val heartRateResponse = healthConnectClient.readRecords(heartRateRequest)

            val allSamples = heartRateResponse.records.flatMap { it.samples }
            val latestSample = allSamples.maxByOrNull { it.time }

            val sleepRequest = ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now()
                )
            )
            val sleepResponse = healthConnectClient.readRecords(sleepRequest)
            val lastSleep = sleepResponse.records.maxByOrNull {it.startTime}

            HealthMetrics(
                heartRate = latestSample?.beatsPerMinute?.toInt(),
                sleepQuality = calculateSleepQuality(lastSleep),
                stressLevel = calculateStressFromHeartRate(latestSample?.beatsPerMinute?.toDouble())
            )
        } catch (e: Exception) {
            Log.e("HealthDataRepository", "Error retrieving health data", e)
            null
        }
    }

    private fun calculateStressFromHeartRate(heartRate: Double?) : Float? {
        return heartRate?.let { hr->
            when {
                hr > 100 -> 80f
                hr > 80 -> 50f
                hr > 60 -> 20f
                else -> 10f
            }
        }
    }

    private fun calculateSleepQuality(sleepSession: SleepSessionRecord?) : Float? {
        return sleepSession?.let { 80f } // Acá falta más implementacion pero mientras dejo esto
    }
}