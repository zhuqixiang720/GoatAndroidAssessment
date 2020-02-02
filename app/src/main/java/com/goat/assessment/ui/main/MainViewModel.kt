package com.goat.assessment.ui.main

import android.content.res.Resources
import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.goat.assessment.R
import com.goat.assessment.service.ServiceRepository
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

    val locationValue = ObservableField("")
    val isOperationInProgress = ObservableField(false)

    fun fetchWeatherForecast(location: Location?) {
        if (location == null) {
            locationValue.set(resources.getString(R.string.city_los_angeles))
            fetchWeatherForecastForLocation()
        } else {
            locationValue.set(resources.getString(R.string.city_current_location))
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
            .doOnSubscribe { isOperationInProgress.set(true) }
            .doOnSuccess { isOperationInProgress.set(false) }
            .doOnError { isOperationInProgress.set(false) }
            .subscribe({ weather ->
            }, { error ->
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
