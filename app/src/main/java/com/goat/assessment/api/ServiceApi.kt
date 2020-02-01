package com.goat.assessment.api

import com.goat.assessment.api.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ServiceApi {
    @GET("weather")
    fun getWeather(): Single<WeatherResponse>
}