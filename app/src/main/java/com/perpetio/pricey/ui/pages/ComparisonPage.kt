package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.ui.theme.plate

@Composable
fun ComparisonPage(
    products: List<Product>,
    onAddToBasket: (List<Product>) -> Unit,
    goToBasket: () -> Unit
) {

    val productHeader = products[0].header
    val selectedProducts = remember {
        mutableStateListOf<Product>()
    }
    Column {
        Image(
            painter = painterResource(productHeader.imageResId),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = stringResource(R.string.product_image)
        )
        ComparisonList(
            products = products,
            selectedProducts = selectedProducts,
            onProductSelect = { product ->
                selectedProducts.add(product)
            }
        )
        Button(
            onClick = {
                onAddToBasket(selectedProducts)
            }
        ) {
            Text(
                text = stringResource(R.string.add_to_basket)
            )
        }
        Button(
            onClick = {
                goToBasket()
            }
        ) {
            Text(
                text = "Go to basket"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ComparisonPage(
        DataProvider.products,
        {},
        {}
    )
}

@Composable
private fun ComparisonList(
    products: List<Product>,
    selectedProducts: List<Product>,
    onProductSelect: (Product) -> Unit,
) {
    val items = remember { products }
    LazyColumn(
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
                    isSelected = selectedProducts.contains(product),
                    onSelect = onProductSelect
                )
            }
        )
    }
}

@Composable
private fun ProductItem(
    product: Product,
    isSelected: Boolean,
    onSelect: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = plate.padding.dp,
                bottom = plate.padding.dp
            )
            .clickable { onSelect(product) }
            .fillMaxWidth(),
        elevation = plate.elevation.dp,
        shape = RoundedCornerShape(plate.corners.dp),
        backgroundColor = if(isSelected) Color.DarkGray else MaterialTheme.colors.surface
    ) {
        Text(
            text = product.header.name,
            modifier = Modifier.padding(plate.padding.dp)
        )
    }
}