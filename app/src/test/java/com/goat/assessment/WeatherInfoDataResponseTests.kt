package com.goat.assessment

import com.goat.assessment.api.model.WeatherInfoDataResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for [WeatherInfoDataResponse]
 */
class WeatherInfoDataResponseTests {
    private lateinit var sut: WeatherInfoDataResponse

    @Before
    fun setUp() {
        val readText = WeatherInfoDataResponseTests::class.java.getResource("/currently.json")!!.readText()
        sut = Gson().fromJson(readText, WeatherInfoDataResponse::class.java)
    }

    @Test
    fun timestamp_isCorrect() {
        assertEquals(1580565475, sut.time)
    }

    @Test
    fun summary_isCorrect() {
        assertEquals("Clear", sut.summary)
    }

    @Test
    fun icon_isCorrect() {
        assertEquals("clear-night", sut.icon)
    }

    @Test
    fun temperature_isCorrect() {
        assertEquals(49.61, sut.temperature, 0.0)
    }

    @Test
    fun apparentTemperature_isCorrect() {
        assertEquals(48.19, sut.apparentTemperature, 0.0)
    }

    @Test
    fun humidity_isCorrect() {
        assertEquals(0.9, sut.humidity, 0.0)
    }

    @Test
    fun windSpeed_isCorrect() {
        assertEquals(4.36, sut.windSpeed, 0.0)
    }


}
