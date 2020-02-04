package com.goat.assessment.ui.main

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goat.assessment.R
import com.goat.assessment.api.model.WeatherResponse
import com.goat.assessment.service.ServiceRepository
import com.goat.assessment.ui.WeatherIcon
import com.goat.assessment.utils.Utils
import com.goat.assessment.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 34.052235
private const val DEFAULT_LONGITUDE = -118.243683

// make the data expire in 1 minutes
private const val DATA_EXPIRE_TIME_MS = 60000L

class MainViewModel @Inject constructor(
    private val resources: Resources,
    private val serviceRepository: ServiceRepository
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val isLoading = ObservableField(false)

    val cityText = ObservableField("")
    val locationText = ObservableField("")
    val timeText = ObservableField("")
    val weatherIcon: ObservableField<Drawable> = ObservableField()
    val weatherText = ObservableField("")
    val tempText = ObservableField("")
    val apparentTempText = ObservableField("")

    private val _serviceError = MutableLiveData<Throwable>()
    val serviceError: LiveData<Throwable> = _serviceError

    private val _weatherInfo = MutableLiveData<WeatherResponse>()
    val weatherInfo: LiveData<WeatherResponse> = _weatherInfo

    fun fetchWeatherForecast(location: Location?, forceRefresh: Boolean) {
        if (location == null) {
            cityText.set(resources.getString(R.string.city_los_angeles))
            locationText.set("$DEFAULT_LATITUDE, $DEFAULT_LONGITUDE")
            fetchWeatherForecastForLocation(forceRefresh = forceRefresh)
        } else {
            cityText.set(resources.getString(R.string.city_current_location))
            locationText.set("${location.latitude}, ${location.longitude}")
            fetchWeatherForecastForLocation(location.latitude, location.longitude, forceRefresh)
        }
    }

    /**
     * Fetch the current weather forecast
     */
    private fun fetchWeatherForecastForLocation(
        latValue: Double = DEFAULT_LATITUDE,
        lonValue: Double = DEFAULT_LONGITUDE,
        forceRefresh: Boolean
    ) {
        isLoading.set(true)

        if (!forceRefresh &&
            weatherInfo.value != null &&
            weatherInfo.value!!.latitude == latValue &&
            weatherInfo.value!!.longitude == lonValue &&
            System.currentTimeMillis() - weatherInfo.value!!.currently.time * 1000 <= DATA_EXPIRE_TIME_MS) {
            isLoading.set(false)
            return
        }

        serviceRepository.getWeatherForecast(latValue, lonValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                isLoading.set(false)
            }
            .doOnError {
                isLoading.set(false)
            }
            .subscribe({ weather ->
                timeText.set(Utils.getDateTimeString(weather.currently.time))
                weatherIcon.set(ResourcesCompat.getDrawable(resources, WeatherIcon.parseWeatherIcon(weather.currently.icon).iconRes, null))
                weatherText.set(resources.getString(WeatherIcon.parseWeatherIcon(weather.currently.icon).stringRes))
                tempText.set(resources.getString(R.string.temp_description, String.format("%.0f", weather.currently.temperature)))
                apparentTempText.set(resources.getString(R.string.apparent_temp_description, String.format("%.0f", weather.currently.apparentTemperature)))
                _weatherInfo.value = weather
            }, { error ->
                _serviceError.value = error
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
