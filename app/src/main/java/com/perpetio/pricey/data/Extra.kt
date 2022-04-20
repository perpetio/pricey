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