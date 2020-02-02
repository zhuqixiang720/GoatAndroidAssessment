package com.goat.assessment.service

import com.goat.assessment.api.ServiceApi
import com.goat.assessment.api.model.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(private val serviceApi: ServiceApi) {

    fun getWeatherForecast(latitude: Double, longitude: Double): Single<WeatherResponse> {
        return serviceApi.getWeatherForecast(latitude, longitude)
    }
}