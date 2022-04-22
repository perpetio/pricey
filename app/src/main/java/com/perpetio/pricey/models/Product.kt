package com.perpetio.pricey.models

import java.util.*

data class Product(
    val article: ProductArticle,
    val store: Store,
    val rating: Int, // 0..5
    val price: Double,
    val amount: Double,
    val expirationDate: Date
) {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other.javaClass != this.javaClass) return false
        val product = other as Product
        if (this.article.name != product.article.name) return false
        if (this.store.chain.name != product.store.chain.name) return false
        return (this.store.remoteness == product.store.remoteness)
    }

    override fun hashCode(): Int {
        return article.name.hashCode() +
                store.chain.name.hashCode() +
                store.remoteness.toInt()
    }

    companion object {
        const val MAX_RATING = 5 // starts
    }
}
