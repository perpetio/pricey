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
        Product(productArticles[0], stores[4], 4,11.0, 5.0, DateManager.dateFromNow(7)),
        Product(productArticles[0], stores[5], 2,10.0, 30.0, DateManager.dateFromNow(4)),
        Product(productArticles[0], stores[7], 3,8.0, 7.0, DateManager.dateFromNow(3)),
        Product(productArticles[0], stores[8], 4,10.0, 12.0, DateManager.dateFromNow(5)),
    )
}