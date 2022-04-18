package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.models.ProductHeader
import com.perpetio.pricey.ui.theme.plate

@Composable
fun ListPage(
    products: List<ProductHeader>,
    onProductClick: (String) -> Unit = {},
) {
    val items = remember { products }
    LazyColumn(
        contentPadding = PaddingValues(plate.padding.dp)
    ) {
        items(
            items = items,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onClick = onProductClick
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ListPage(
        listOf(),
        {}
    )
}

@Composable
private fun ProductItem(
    product: ProductHeader,
    onClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(bottom = plate.padding.dp)
            .clickable { onClick(product.name) }
            .fillMaxWidth(),
        elevation = plate.elevation.dp,
        shape = RoundedCornerShape(plate.corners.dp)
    ) {
        Text(
            text = product.name,
            modifier = Modifier.padding(plate.padding.dp)
        )
    }
}