package com.goat.assessment.ui.details

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.goat.assessment.api.model.WeatherInfoDataResponse
import com.goat.assessment.utils.Utils

class DetailsHourlyItemViewModel: ViewModel() {
    val hourText = ObservableField("")
    val summaryText = ObservableField("")
    val tempText = ObservableField("")
    val isHourExpired = ObservableField(false)

    fun seItemInfo(hourlyItemInfo: WeatherInfoDataResponse) {
        hourText.set(Utils.getHourString(hourlyItemInfo.time))
        summaryText.set(hourlyItemInfo.summary)
        tempText.set(String.format("%.0f", hourlyItemInfo.temperature))
        isHourExpired.set(System.currentTimeMillis() > hourlyItemInfo.time * 1000)
    }
}