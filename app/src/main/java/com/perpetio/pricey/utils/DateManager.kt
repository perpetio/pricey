package com.perpetio.pricey.utils

import java.util.*

object DateManager {
    fun dateFromNow(
        dayOffset: Int
    ): Date {
        Calendar.getInstance().apply {
            add(Calendar.DATE, dayOffset)
            return time
        }
    }

    fun getDaysToDate(
        date: Date
    ): Int {
        val diff = date.time - Date().time
        return (diff / (24 * 60 * 60 * 1000)).toInt()
    }
}