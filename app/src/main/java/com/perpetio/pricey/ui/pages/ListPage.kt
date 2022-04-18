package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.models.ProductHeader
import com.perpetio.pricey.ui.theme.plate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListPage(
    products: List<ProductHeader>,
    onProductSelect: (String) -> Unit,
) {
    val items = remember { products }
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = plate.padding.dp,
            top = plate.padding.dp
        )
    ) {
        items(
            items = items,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onSelect = onProductSelect
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ListPage(
        DataProvider.productsHeaders,
        {}
    )
}

@Composable
private fun ProductItem(
    product: ProductHeader,
    onSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = plate.padding.dp,
                bottom = plate.padding.dp
            )
            .clickable { onSelect(product.name) }
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