package com.perpetio.pricey.mocks

import com.perpetio.pricey.R
import com.perpetio.pricey.models.*
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

    val categories = listOf(
        FoodCategory("Fruits", R.drawable.ic_fruits),
        FoodCategory("Vegetables", R.drawable.ic_vegetables),
        FoodCategory("Bakery", R.drawable.ic_bakery),
        FoodCategory("Meat", R.drawable.ic_meat),
        FoodCategory("Fruits", R.drawable.ic_fruits)
    )

    val articles = listOf(
        ProductArticle(categories[0], "Banana", null, "kg"),
        ProductArticle(categories[0], "Apple", null, "kg"),
        ProductArticle(categories[0], "Orange", null, "kg"),
        ProductArticle(categories[0], "Kiwi", null, "kg")
    )

    val products = listOf(
        Product(articles[0], stores[0], 12.0, 15.0, Date()),
        Product(articles[0], stores[1], 13.0, 20.0, Date()),
    )

    fun getProducts(name: String): List<Product> {
        val result = mutableListOf<Product>()
        products.forEach { product ->
            if (product.article.name == name) {
                result.add(product)
            }
        }
        return result
    }
}