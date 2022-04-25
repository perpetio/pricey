package com.perpetio.pricey.models

class BasketProduct(
    product: Product,
    var selectedAmount: Double = 1.0
) : Product(
    product.article,
    product.store,
    product.rating,
    product.price,
    product.amount,
    product.expirationDate
)