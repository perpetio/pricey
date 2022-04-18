package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.Product

class BasketViewModel : ViewModel() {
    var products = mutableStateListOf<Product>()

    fun addToBasket(newProducts: List<Product>) {
        newProducts.forEach { newProduct ->
            val foundProduct = products.find { oldProduct ->
                newProduct.article.name == oldProduct.article.name
                        && newProduct.store.name == oldProduct.store.name
            }
            if (foundProduct == null) {
                products.add(newProduct)
            }
        }
    }

    fun removeFromBasket(product: Product) {
        products.remove(product)
    }
}