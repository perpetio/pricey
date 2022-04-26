package com.perpetio.pricey.data

val RatingStarts  = 1..5

enum class ExpirationPeriod(
    val rangeInDays: String
) {
    UpTo2("0-2"),
    UpTo7("3-7"),
    UpTo13("8-13"),
    StartWith14("14+"),
}