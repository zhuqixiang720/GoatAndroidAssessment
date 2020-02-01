package com.goat.assessment.api.model

import androidx.annotation.Keep

/**
 * Weather info response.
 *
 * ```
 *   {
 *      "summary":"Rain starting later this afternoon, continuing until this evening.",
 *      "icon":"rain",
 *      "data":[]
 *   }
 * ```
 */
@Keep
class WeatherInfoResponse(
    val summary: String,
    val icon: String,
    val data: List<WeatherInfoDataResponse>
)