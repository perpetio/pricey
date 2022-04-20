package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle

class FilterViewModel : ViewModel() {
    val selectedArticle = mutableStateOf<ProductArticle?>(null)

    fun getProducts(
        productArticle: ProductArticle,
        filter: Filter,
        sortType: SortType
    ): List<Product> {
        val products = DataProvider
            .getProducts(productArticle.name)
            .toMutableList()
        when (filter) {
            Filter.Price -> products.sortBy { it.price }
            Filter.Rating -> products.sortBy { it.rating }
            Filter.Expiration -> products.sortBy { it.expirationDate }
        }
        if (sortType == SortType.Descending) {
            products.reverse()
        }
        return products
    }
}