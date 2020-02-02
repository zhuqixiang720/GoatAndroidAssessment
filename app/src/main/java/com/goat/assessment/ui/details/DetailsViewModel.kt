package com.goat.assessment.ui.details

import androidx.lifecycle.ViewModel
import com.goat.assessment.api.model.WeatherInfoResponse
import javax.inject.Inject

class DetailsViewModel @Inject constructor(): ViewModel() {
    private var hourlyInfo: WeatherInfoResponse? = null

    fun setHourlyInfo(hourly: WeatherInfoResponse) {
        hourlyInfo = hourly
    }
}