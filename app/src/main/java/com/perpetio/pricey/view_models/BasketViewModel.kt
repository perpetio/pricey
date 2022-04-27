package com.perpetio.pricey.view_models

import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.BasketProduct
import com.perpetio.pricey.models.Product

class BasketViewModel : ViewModel() {
    private val _basketList = mutableListOf<BasketProduct>()
    val basketList: List<BasketProduct> = _basketList

    fun addToBasket(products: List<Product>) {
        products.forEach { product ->
            if (!_basketList.contains(product)) {
                _basketList.add(
                    BasketProduct(product)
                )
            }
        }
    }

    fun updateBasketList(newList: List<BasketProduct>) {
        if (newList == _basketList) return
        _basketList.clear()
        _basketList.addAll(newList)
    }
}