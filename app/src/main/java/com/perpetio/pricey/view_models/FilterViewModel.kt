package com.perpetio.pricey.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.FoodCategory
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle

class FilterViewModel : ViewModel() {
    val productArticle = mutableStateOf<ProductArticle?>(null)

    fun searchOfProductArticles(
        partOfProductName: String,
        foodCategory: FoodCategory
    ): List<ProductArticle> {
        val result = mutableListOf<ProductArticle>()
        DataProvider.productArticles.forEach { article ->
            article.apply {
                if (this.foodCategory != foodCategory) return@apply
                if (!this.name.startsWith(partOfProductName, true)) return@apply
                result.add(article)
            }
        }
        return result
    }

    fun filterProducts(
        productArticle: ProductArticle,
        filter: Filter,
        sortType: SortType
    ): List<Product> {
        val products = getProducts(productArticle.name).toMutableList()
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

    private fun getProducts(
        name: String
    ): List<Product> {
        val result = mutableListOf<Product>()
        DataProvider.products.forEach { product ->
            if (product.article.name == name) {
                result.add(product)
            }
        }
        return result
    }
}