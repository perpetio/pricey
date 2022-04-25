package com.perpetio.pricey.utils

fun Float.toPrice(digits: Int = 2) = "%.${digits}f".format(this)