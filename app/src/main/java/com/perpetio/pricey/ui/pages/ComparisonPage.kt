package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    products: List<Product>
) {
    val productHeader = products[0].header
    Column {
        Image(
            painter = painterResource(productHeader.imageResId),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = stringResource(R.string.product_image)
        )
        Text(
            text = productHeader.name,
            modifier = Modifier.padding(plate.padding.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ComparisonPage(
        DataProvider.products
    )
}