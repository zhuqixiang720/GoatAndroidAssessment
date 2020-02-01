package com.goat.assessment

import com.goat.assessment.api.model.WeatherInfoResponse
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [WeatherInfoResponse]
 */
class WeatherInfoResponseTests {
    private lateinit var sut: WeatherInfoResponse

    @Before
    fun setUp() {
        val readText = WeatherInfoResponseTests::class.java.getResource("/hourly.json")!!.readText()
        sut = Gson().fromJson(readText, WeatherInfoResponse::class.java)
    }

    @Test
    fun summary_isCorrect() {
        assertEquals("Partly cloudy throughout the day.", sut.summary)
    }

    @Test
    fun icon_isCorrect() {
        assertEquals("partly-cloudy-day", sut.icon)
    }

    @Test
    fun data_isCorrect() {
        assertEquals(24, sut.data.size)
        // verify the first element
        assertEquals(1580562000, sut.data[0].time)
        assertEquals("Clear", sut.data[0].summary)
        assertEquals("clear-night", sut.data[0].icon)
        assertEquals(50.38, sut.data[0].temperature, 0.0)
        assertEquals(50.38, sut.data[0].apparentTemperature, 0.0)
        assertEquals(0.88, sut.data[0].humidity, 0.0)
        assertEquals(4.57, sut.data[0].windSpeed, 0.0)
    }

}
