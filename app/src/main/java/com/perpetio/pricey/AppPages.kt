package com.perpetio.pricey

enum class AppPages {
    ListPage, ComparisonPage, BasketPage;

    companion object {
        fun fromRoute(route: String?): AppPages =
            when (route?.substringBefore("/")) {
                ListPage.name -> ListPage
                ComparisonPage.name -> ComparisonPage
                BasketPage.name -> BasketPage
                else -> ListPage
            }
    }
}