package com.goat.assessment.api.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**
 * Weather info data response.
 *
 * ```
 *   {
 *      "time":1580709600,
 *      "summary":"Clear",
 *      "icon": "partly-cloudy-day",
 *      "temperature":46.1,
 *      "apparentTemperature":39.23,
 *      "humidity":0.7
 *      "windSpeed":16.64
 *   }
 * ```
 */

@Keep
@Parcelize
class WeatherInfoDataResponse(
    val time: Long,
    val summary: String,
    val icon: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val humidity: Double,
    val windSpeed: Double
) : Parcelable