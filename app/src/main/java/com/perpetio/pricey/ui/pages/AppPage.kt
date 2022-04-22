package com.perpetio.pricey.ui.pages

import com.perpetio.pricey.R

enum class AppPage(
    val iconResId: Int? = null
) {
    ListPage(
        iconResId = R.drawable.ic_home
    ),
    ComparisonPage,
    FilterPage,
    BasketPage(
        iconResId =R.drawable.ic_basket
    );

    companion object {
        fun fromRoute(route: String?): AppPage =
            when (route?.substringBefore("/")) {
                ListPage.name -> ListPage
                ComparisonPage.name -> ComparisonPage
                FilterPage.name -> FilterPage
                BasketPage.name -> BasketPage
                else -> ListPage
            }
    }
}