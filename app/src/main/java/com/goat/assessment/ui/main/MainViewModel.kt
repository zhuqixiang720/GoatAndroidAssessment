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
import com.goat.assessment.api.model.WeatherInfoResponse
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

    var weatherHourlyInfo: WeatherInfoResponse? = null
        private set

    private val _detailsPageEvent = MutableLiveData<Unit>()
    val detailsPageEvent: LiveData<Unit> = _detailsPageEvent

    fun fetchWeatherForecast(location: Location?) {
        if (location == null) {
            cityText.set(resources.getString(R.string.city_los_angeles))
            locationText.set("$DEFAULT_LATITUDE, $DEFAULT_LONGITUDE")
            fetchWeatherForecastForLocation()
        } else {
            cityText.set(resources.getString(R.string.city_current_location))
            locationText.set("${location.latitude} , ${location.longitude}")
            fetchWeatherForecastForLocation(location.latitude, location.longitude)
        }
    }

    /**
     * Fetch the current weather forecast
     */
    private fun fetchWeatherForecastForLocation(
        latValue: Double = DEFAULT_LATITUDE,
        lonValue: Double = DEFAULT_LONGITUDE
    ) {
        serviceRepository.getWeatherForecast(latValue, lonValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.set(true)
            }
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
                // set the weather hourly info
                weatherHourlyInfo = weather.hourly
            }, { error ->
                _serviceError.value = error
            })
            .addTo(compositeDisposable)
    }

    fun showDetailsPage() {
        _detailsPageEvent.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
