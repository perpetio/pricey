package com.perpetio.pricey.models

data class ProductArticle(
    val foodCategory: FoodCategory,
    val name: String,
    val imageResId: Int?,
    val amountUnit: String
)