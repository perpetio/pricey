package com.perpetio.pricey.data

val RatingStarts = 1..5

enum class ExpirationPeriod(
    private val rangeInDays: IntRange
) {
    Any(0..Int.MAX_VALUE),
    UpTo2(0..2),
    UpTo7(3..7),
    UpTo13(8..13),
    StartWith14(14..Int.MAX_VALUE);

    fun contains(days: Int): Boolean {
        return rangeInDays.contains(days)
    }

    override fun toString(): String {
        rangeInDays.apply {
            return "$start" +
                    if (endInclusive < Int.MAX_VALUE) {
                        "-$endInclusive"
                    } else "+"
        }
    }
}