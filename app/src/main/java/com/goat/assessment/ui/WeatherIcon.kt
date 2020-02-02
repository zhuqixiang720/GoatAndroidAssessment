package com.goat.assessment.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.goat.assessment.R

enum class IconType(@DrawableRes val iconRes: Int, @StringRes val stringRes: Int) {
    CLEAR_DAY(R.drawable.skyconcleardaywhite_48, R.string.weather_clear),
    CLEAR_NIGHT(R.drawable.skyconclearnightwhite_48, R.string.weather_clear),
    CLOUDY(R.drawable.skyconcloudywhite_48, R.string.weather_cloudy),
    PARTLY_CLOUDY_DAY(R.drawable.skyconpartlycloudydaywhite_48, R.string.weather_partly_cloudy),
    PARTLY_CLOUDY_NIGHT(R.drawable.skyconcloudywhite_48, R.string.weather_partly_cloudy),
    RAIN(R.drawable.skyconrainwhite_48, R.string.weather_wind),
    WIND(R.drawable.skyconwindwhite_48, R.string.weather_wind),
    FOG(R.drawable.skyconfogwhite_48, R.string.weather_fog),
    OTHER(R.drawable.darkskylogo_small_white_48, R.string.weather_other)
}

/**
 * The class to represent the weather icon
 */
object WeatherIcon {
    fun parseWeatherIcon(iconText: String): IconType {
        return when (iconText) {
            "clear-day" -> IconType.CLEAR_DAY
            "clear-night" -> IconType.CLEAR_NIGHT
            "cloudy" -> IconType.CLOUDY
            "partly-cloudy-day" -> IconType.PARTLY_CLOUDY_DAY
            "partly-cloudy-night" -> IconType.PARTLY_CLOUDY_NIGHT
            "rain" -> IconType.RAIN
            "wind" -> IconType.WIND
            "fog" -> IconType.FOG
            else -> IconType.OTHER
        }
    }
}