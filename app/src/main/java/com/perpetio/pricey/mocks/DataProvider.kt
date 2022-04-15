package com.perpetio.pricey.mocks

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
        ProductHeader("Гречка", "", ""),
        ProductHeader("Macло", "", ""),
        ProductHeader("Сир", "", ""),
        ProductHeader("Вареники", "", ""),
        ProductHeader("Молоко", "", ""),
        ProductHeader("Печиво", "", ""),
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