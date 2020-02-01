package com.goat.assessment.api.model

import androidx.annotation.Keep

/**
 * Detailed Weather data response.
 * ```
 *   {
 *      "time":1580709600,
 *      "summary":"Clear",
 *      "icon": "partly-cloudy-day",
 *      "temperature":46.1,
 *      "apparentTemperature":39.23,
 *      "humidity":0.7
 *      "windSpeed":16.64,
 *      "precipIntensity":0.0017,
 *      "precipProbability":0.02,
 *      "precipType": "rain"
 *   }
 * ```
 */

@Keep
class WeatherDataResponse(
    val timestamp: Long,
    val summary: String,
    val icon: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val precipIntensity: Double,
    val precipProbability: Double,
    val precipType: String
)