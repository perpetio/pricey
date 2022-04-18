package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.ui.theme.plate

@Composable
fun BasketPage(
    products: List<Product>,
    onProductRemove: (String, String) -> Unit,
    onGoBack: () -> Unit
) {
    val items = remember { products }
    LazyColumn(
        contentPadding = PaddingValues(
            start = plate.padding.dp,
            top = plate.padding.dp,
            end = plate.padding.dp,
        )
    ) {
        items(
            items = items,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onRemove = onProductRemove
                )
            }
        )
    }
    Button(
        onClick = { onGoBack() }
    ) {
        Text(text = "GoBack")
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BasketPage(
        emptyList(),
        { _, _ -> },
        {}
    )
}

@Composable
private fun ProductItem(
    product: Product,
    onRemove: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = plate.padding.dp,
                bottom = plate.padding.dp
            )
            .clickable {
                onRemove(product.header.name, product.shop.name)
            }
            .fillMaxWidth(),
        elevation = plate.elevation.dp,
        shape = RoundedCornerShape(plate.corners.dp)
    ) {
        Text(
            text = product.header.name,
            modifier = Modifier.padding(plate.padding.dp)
        )
    }
}