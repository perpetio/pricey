package com.perpetio.pricey.data

import com.perpetio.pricey.R

enum class Filter(
    val resId: Int
) {
    Price(R.string.price),
    Rating(R.string.rating),
    Expiration(R.string.expiration)
}

enum class SortType {
    Ascending, Descending;

    fun getOpposite(): SortType {
        return if (this == Ascending) {
            Descending
        } else Ascending
    }
}

object RatingStarts {
    const val min = 0
    const val max = 5
}

enum class ExpirationPeriod(
    val days: Int
) {
    UpTo2(2),
    UpTo7(7),
    UpTo13(13),
    StartWith14(14),
}