package com.perpetio.pricey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductHeader
import com.perpetio.pricey.ui.theme.PriceyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PriceyTheme {
                PageUi()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageUi() {
    Scaffold (
        content = {
            ListOfProducts()
        }
    )
}

@Composable
fun ListOfProducts() {
    val products = remember {
        DataProvider.productsHeaders
    }
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.plate_padding))
    ) {
        items(
            items = products,
            itemContent = { productHeader ->
                ProductItem(productHeader = productHeader)
            }
        )
    }
}

@Composable
fun ProductItem(productHeader: ProductHeader) {
    Card(
        modifier = Modifier
            .padding(bottom = dimensionResource(R.dimen.plate_padding))
            .fillMaxWidth(),
        elevation = dimensionResource(R.dimen.plate_elevation),
        shape = RoundedCornerShape(dimensionResource(R.dimen.plate_corners))
    ) {
        Text(
            text = productHeader.name,
            modifier = Modifier.padding(dimensionResource(R.dimen.plate_padding))
        )
    }
}