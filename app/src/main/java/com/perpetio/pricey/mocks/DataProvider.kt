package com.perpetio.pricey.mocks

import com.perpetio.pricey.R
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductHeader
import com.perpetio.pricey.models.Shop
import java.util.*

object DataProvider {
    val shops = listOf(
        Shop("Shop1", "test uri"),
        Shop("Shop2", "test uri")
    )

    val productsHeaders = listOf(
        ProductHeader("Гречка", R.drawable.ic_launcher_background, ""),
        ProductHeader("Macло", R.drawable.ic_launcher_background, ""),
        ProductHeader("Сир", R.drawable.ic_launcher_background, ""),
        ProductHeader("Вареники", R.drawable.ic_launcher_background, ""),
        ProductHeader("Молоко", R.drawable.ic_launcher_background, ""),
        ProductHeader("Печиво", R.drawable.ic_launcher_background, ""),
    )

    val products = listOf(
        Product(productsHeaders[0], shops[0], 12.0, 15.0, Date()),
        Product(productsHeaders[0], shops[1], 13.0, 20.0, Date()),
    )

    fun getProducts(name: String): List<Product> {
        val result = mutableListOf<Product>()
        products.forEach { product ->
            if (product.header.name == name) {
                result.add(product)
            }
        }
        return result
    }
}