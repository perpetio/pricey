package com.perpetio.pricey.view_models

import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.Store

class BasketViewModel : ViewModel() {
    private val _basketList = mutableListOf<Product>()
    val basketList: List<Product> = _basketList

    fun addToBasket(products: List<Product>) {
        products.forEach { product ->
            if (!_basketList.contains(product)) {
                _basketList.add(
                    Product(
                        amount = 1.0,
                        data = product
                    )
                )
            }
        }
    }

    fun removeFromList(extraProducts: List<Product>) {
        _basketList.removeAll(extraProducts)
    }
}