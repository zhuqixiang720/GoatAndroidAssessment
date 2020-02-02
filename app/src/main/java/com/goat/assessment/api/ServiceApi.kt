package com.goat.assessment.api

import com.goat.assessment.api.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {
    @GET("{latitude},{longitude}?exclude=minutely,alerts,flags")
    fun getWeatherForecast(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Single<WeatherResponse>
}