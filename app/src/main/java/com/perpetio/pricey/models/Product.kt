package com.perpetio.pricey.models

import java.util.*

data class Product(
    val header: ProductHeader,
    val shop: Shop,
    val price: Double,
    val amount: Double,
    val expirationDate: Date
)
