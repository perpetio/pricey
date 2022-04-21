package com.perpetio.pricey.view_models

import androidx.lifecycle.ViewModel
import com.perpetio.pricey.models.Product

class BasketViewModel : ViewModel() {
    private val _basketList = mutableListOf<Product>()
    val basketList: List<Product> = _basketList

    fun updateList(products: List<Product>) {
        _basketList.clear()
        _basketList.addAll(products)
    }

    fun removeFromList(extraProducts: List<Product>) {
        _basketList.removeAll(extraProducts)
    }
}