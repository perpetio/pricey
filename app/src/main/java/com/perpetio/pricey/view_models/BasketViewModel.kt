package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.Product

class BasketViewModel : ViewModel() {
    var products = mutableStateListOf<Product>()

    fun addToBasket(product: Product) {
        products.add(product)
    }
    fun removeFromBasket(
        productName: String,
        shopName: String
    ) {
        products.find { product ->
            product.header.name == productName
                && product.shop.name == shopName
        }?.let { targetProduct ->
            products.remove(targetProduct)
        }
    }
}