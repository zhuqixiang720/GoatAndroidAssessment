package com.goat.assessment.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun getDateTimeString(timestamp: Long): String {
        val sdf =  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val date = java.util.Date(timestamp * 1000)
        return sdf.format(date)
    }

    fun getHourString(timestamp: Long): String {
        val sdf =  SimpleDateFormat("h a", Locale.US)
        val date = java.util.Date(timestamp * 1000)
        return sdf.format(date)
    }

    fun getDateHourString(timestamp: Long): String {
        val sdf =  SimpleDateFormat("h a yyyy-MM-dd", Locale.US)
        val date = java.util.Date(timestamp * 1000)
        return sdf.format(date)
    }
}