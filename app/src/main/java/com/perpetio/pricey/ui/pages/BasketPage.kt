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
import com.perpetio.pricey.ui.theme.Plate

@Composable
fun BasketPage(
    products: List<Product>,
    onProductRemove: (Product) -> Unit,
    onGoBack: () -> Unit
) {
    val items = remember { products }
    LazyColumn(
        contentPadding = PaddingValues(
            start = Plate.padding.dp,
            top = Plate.padding.dp,
            end = Plate.padding.dp,
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
        {},
        {}
    )
}

@Composable
private fun ProductItem(
    product: Product,
    onRemove: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = Plate.padding.dp,
                bottom = Plate.padding.dp
            )
            .clickable {
                onRemove(product)
            }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp)
    ) {
        Text(
            text = product.article.name,
            modifier = Modifier.padding(Plate.padding.dp)
        )
    }
}