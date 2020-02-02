package com.goat.assessment.api.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

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
@Parcelize
class WeatherInfoResponse(
    val summary: String,
    val icon: String,
    val data: List<WeatherInfoDataResponse>
) : Parcelable