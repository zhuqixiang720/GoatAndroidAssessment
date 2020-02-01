package com.goat.assessment

import com.goat.assessment.api.model.WeatherResponse
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [WeatherResponse]
 */
class WeatherResponseTests {
    private lateinit var sut: WeatherResponse

    @Before
    fun setUp() {
        val readText = WeatherResponseTests::class.java.getResource("/weather.json")!!.readText()
        sut = Gson().fromJson(readText, WeatherResponse::class.java)
    }

    @Test
    fun latitude_isCorrect() {
        assertEquals(37.8267, sut.latitude, 0.0)
    }

    @Test
    fun longitude_isCorrect() {
        assertEquals(-122.4233, sut.longitude, 0.0)
    }

    @Test
    fun timezone_isCorrect() {
        assertEquals("America/Los_Angeles", sut.timezone)
    }

    @Test
    fun offset_isCorrect() {
        assertEquals(-8, sut.offset)
    }

    @Test
    fun currently_isNotNull() {
        assertNotNull(sut.currently)
    }

    @Test
    fun hourly_isNotNull() {
        assertNotNull(sut.hourly)
    }

    @Test
    fun daily_isNotNull() {
        assertNotNull(sut.daily)
    }

}
