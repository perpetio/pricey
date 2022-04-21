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
}