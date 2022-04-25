package com.perpetio.pricey.models

import java.util.*

open class Product(
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
        if (other !is Product) return false
        if (this.article.name != other.article.name) return false
        if (this.store.chain.name != other.store.chain.name) return false
        return (this.store.remoteness == other.store.remoteness)
    }

    override fun hashCode(): Int {
        return article.name.hashCode() +
                store.chain.name.hashCode() +
                store.remoteness.toInt()
    }
}
