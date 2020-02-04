package com.goat.assessment.ui.details

import android.content.res.Resources
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.goat.assessment.R
import com.goat.assessment.api.model.WeatherInfoResponse
import com.goat.assessment.utils.Utils

class DetailsHourlyHeaderViewModel(private val resources: Resources): ViewModel() {
    val lowTempText = ObservableField("")
    val highTempText = ObservableField("")
    val summaryText = ObservableField("")
    val timeText = ObservableField("")

    fun setWeatherInfo(weatherInfoResponse: WeatherInfoResponse) {
        summaryText.set(weatherInfoResponse.summary)
        timeText.set(resources.getString(R.string.from_time, Utils.getDateHourString(weatherInfoResponse.data[0].time)))
        // start from index 1 since index 0 is the current hour
        val targetHoursInfo = weatherInfoResponse.data.subList(1, 25)
        lowTempText.set(String.format("%.0f", targetHoursInfo.minBy { it.temperature }?.temperature))
        highTempText.set(String.format("%.0f", targetHoursInfo.maxBy { it.temperature }?.temperature))
    }
}