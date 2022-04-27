package com.perpetio.pricey.data

import com.perpetio.pricey.R
import com.perpetio.pricey.models.*
import com.perpetio.pricey.utils.DateManager
import java.util.*

object DataProvider {

    private val storeChains = listOf(
        StoreChain("Costco", R.drawable.ic_costco),
        StoreChain("Publix", R.drawable.ic_publix),
        StoreChain("Walmart", R.drawable.ic_walmart)
    )

    val stores = listOf(
        Store(storeChains[0], 1.2),
        Store(storeChains[0], 2.5),
        Store(storeChains[0], 4.0),

        Store(storeChains[1], 0.9),
        Store(storeChains[1], 1.5),
        Store(storeChains[1], 2.7),

        Store(storeChains[2], 0.5),
        Store(storeChains[2], 3.1),
        Store(storeChains[2], 5.1),
    )

    val foodCategories = listOf(
        FoodCategory("Fruits", R.drawable.ic_fruits),
        FoodCategory("Vegetables", R.drawable.ic_vegetables),
        FoodCategory("Bakery", R.drawable.ic_bakery),
        FoodCategory("Meat", R.drawable.ic_meat)
    )

    val productArticles = listOf(
        ProductArticle(foodCategories[0], "Banana", R.drawable.fruit_banana, "kg"),
        ProductArticle(foodCategories[0], "Apple", R.drawable.fruit_apple, "kg"),
        ProductArticle(foodCategories[0], "Orange", R.drawable.fruit_orange, "kg"),
        ProductArticle(foodCategories[0], "Kiwi", R.drawable.fruit_kiwi, "kg"),
        ProductArticle(foodCategories[0], "Pomegranate", R.drawable.fruir_pomegranate, "kg"),
        ProductArticle(foodCategories[0], "Pear", R.drawable.fruit_pear, "kg")
    )

    val products = listOf(
        Product(productArticles[0], stores[0], 3,12.0, 15.0, DateManager.dateFromNow(2)),
        Product(productArticles[0], stores[1], 5,13.0, 10.0, DateManager.dateFromNow(5)),
        Product(productArticles[0], stores[4], 4,11.50, 5.0, DateManager.dateFromNow(7)),
        Product(productArticles[0], stores[5], 2,10.0, 30.0, DateManager.dateFromNow(4)),
        Product(productArticles[0], stores[7], 3,8.99, 7.0, DateManager.dateFromNow(15)),
        Product(productArticles[0], stores[8], 4,10.90, 12.0, DateManager.dateFromNow(5)),

        Product(productArticles[1], stores[2], 3,17.90, 11.0, DateManager.dateFromNow(2)),
        Product(productArticles[1], stores[3], 5,21.0, 14.0, DateManager.dateFromNow(3)),
        Product(productArticles[1], stores[5], 4,30.0, 8.0, DateManager.dateFromNow(8)),
        Product(productArticles[1], stores[7], 2,19.50, 7.0, DateManager.dateFromNow(14)),
        Product(productArticles[1], stores[8], 3,27.0, 7.0, DateManager.dateFromNow(9)),
        Product(productArticles[1], stores[1], 4,24.50, 10.0, DateManager.dateFromNow(1)),

        Product(productArticles[2], stores[5], 3,12.20, 8.0, DateManager.dateFromNow(7)),
        Product(productArticles[2], stores[7], 5,25.50, 10.0, DateManager.dateFromNow(11)),
        Product(productArticles[2], stores[3], 1,40.0, 12.0, DateManager.dateFromNow(2)),
        Product(productArticles[2], stores[2], 2,27.80, 7.0, DateManager.dateFromNow(1)),
        Product(productArticles[2], stores[1], 3,33.90, 11.0, DateManager.dateFromNow(15)),
        Product(productArticles[2], stores[0], 4,31.50, 14.0, DateManager.dateFromNow(4))
    )
}