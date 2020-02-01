package com.goat.assessment.api.model

import androidx.annotation.Keep

/**
 * Weather response.
 *
 * ```
 *   {
 *      "latitude":37.8267,
 *      "longitude":-122.4233,
 *      "timezone":"America/Los_Angeles",
 *      "offset":-8
 *      "currently":{  },
 *      "hourly":{  },
 *      "daily":{  },
 *  }
 * ```
 */
@Keep
class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val offset: Int,
    val currently: WeatherInfoDataResponse,
    val hourly: WeatherInfoResponse,
    val daily: WeatherInfoResponse
)
