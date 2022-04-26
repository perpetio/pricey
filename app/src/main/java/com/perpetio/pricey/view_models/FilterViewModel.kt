package com.perpetio.pricey.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.ExpirationPeriod
import com.perpetio.pricey.data.SortValue
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.FoodCategory
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.utils.DateManager

class FilterViewModel : ViewModel() {
    val productArticle = mutableStateOf<ProductArticle?>(null)
    var priceFilter by mutableStateOf(0f..Float.MAX_VALUE)
    var ratingFilter by mutableStateOf(1)
    var expirationFilter by mutableStateOf(ExpirationPeriod.UpTo2)

    fun getPriceRange(
        products: List<Product>
    ): ClosedFloatingPointRange<Float> {
        var minPrice = 0.0
        var maxPrice = minPrice
        products.forEach { product ->
            if (product.price < minPrice) {
                minPrice = product.price
            } else if (product.price > maxPrice) {
                maxPrice = product.price
            }
        }
        return minPrice.toFloat()..maxPrice.toFloat()
    }

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
        priceFilter: ClosedFloatingPointRange<Float>,
        ratingFilter: Int,
        expirationFilter: ExpirationPeriod
    ): List<Product> {
        val filteredList = mutableListOf<Product>()
        getProducts(productArticle.name).forEach { product ->
            product.apply {
                if (price < priceFilter.start) return@apply
                if (price > priceFilter.endInclusive) return@apply
                if (rating < ratingFilter) return@apply
                val days = DateManager.getDaysToDate(expirationDate)
                Log.d("123", "diffDays: $days")
                if (!expirationFilter.contains(days)) return@apply
                filteredList.add(this)
            }
        }
        return filteredList
    }

    fun sortProducts(
        products: List<Product>,
        sortValue: SortValue,
        sortType: SortType
    ): List<Product> {
        val sortedList = products.toMutableList()
        when (sortValue) {
            SortValue.Price -> sortedList.sortBy { it.price }
            SortValue.Rating -> sortedList.sortBy { it.rating }
            SortValue.Expiration -> sortedList.sortBy { it.expirationDate }
        }
        if (sortType == SortType.Descending) {
            sortedList.reverse()
        }
        return sortedList
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