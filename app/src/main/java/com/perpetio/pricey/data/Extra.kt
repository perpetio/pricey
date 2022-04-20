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

    fun getOpposite(
        sortType: SortType
    ): SortType {
        return if (sortType == Ascending) {
            Descending
        } else Ascending
    }
}