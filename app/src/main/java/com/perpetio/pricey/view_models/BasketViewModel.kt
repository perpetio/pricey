package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.Product

class BasketViewModel : ViewModel() {
    val products = mutableStateListOf<Product>()

    fun addToBasket(newProducts: List<Product>) {
        newProducts.forEach { newProduct ->
            if (!products.contains(newProduct)) {
                products.add(newProduct)
            }
        }
    }

    fun removeFromBasket(product: Product) {
        products.remove(product)
    }
}