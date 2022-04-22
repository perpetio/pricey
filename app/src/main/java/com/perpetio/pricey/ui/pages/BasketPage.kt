package com.perpetio.pricey.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.Store
import com.perpetio.pricey.ui.theme.*

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    BasketPage(
//        emptyList(),
//        {},
//        {}
//    )
    StoreItem(
        store = DataProvider.stores[0],
        products = DataProvider.products
    )
}

@Composable
fun BasketPage(
    basketList: List<Product>,
    onProductRemove: (Product) -> Unit,
    goBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(AppColors.LightOrange)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceStyle.main.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(AppColors.Orange),
                modifier = Modifier
                    .size(ButtonStyle.size.dp)
                    .padding(ButtonStyle.padding.dp)
                    .align(Alignment.CenterStart)
                    .clickable { goBack() }
            )
            Text(
                text = stringResource(R.string.cart),
                style = Text.Style(Text.Size.Max, AppColors.Orange).value,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        ListOfStores(basketList = basketList)
    }
}

@Composable
private fun ListOfStores(
    basketList: List<Product>
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        var store: Store? = null
        var products = mutableListOf<Product>()
        Log.d("123", "Basket list: $basketList")
        basketList.sortedBy { it.store.chain.name }.forEach { product ->
            if (store != product.store) {
                store?.let {
                    item {
                        StoreItem(
                            store = it,
                            products = products
                        )
                    }
                }
                store = product.store
                products = mutableListOf()
            } else products.add(product)
        }
        if (products.isNotEmpty()) {
            item {
                StoreItem(
                    store = products.first().store,
                    products = products
                )
            }
        }
    }
}

@Composable
private fun StoreItem(
    store: Store,
    products: List<Product>
) {
    var total = 0.0
    products.forEach { product ->
        total += product.price * product.amount
    }
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(store.chain.imageResId),
                contentDescription = "Store chain image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(70.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${store.remoteness} ${stringResource(R.string.km)}",
                style = Text.Style(Text.Size.Main, fontWeight = FontWeight.ExtraBold).value
            )
        }
        Divider(
            color = AppColors.Gray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ListOfProducts(
            products = products,
            onProductRemove = {},
            onAmountIncrease = {},
            onAmountDecrease = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.total),
                style = Text.Style(Text.Size.Main, fontWeight = FontWeight.ExtraBold).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${total} ${stringResource(R.string.dollar)}",
                style = Text.Style(Text.Size.Title, AppColors.Orange).value,
            )
        }
    }
}

@Composable
private fun ListOfProducts(
    products: List<Product>,
    onProductRemove: (Product) -> Unit,
    onAmountIncrease: (Product) -> Unit,
    onAmountDecrease: (Product) -> Unit,
) {
    LazyColumn {
        items(
            items = products,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onProductRemove = onProductRemove,
                    onAmountIncrease = onAmountIncrease,
                    onAmountDecrease = onAmountDecrease,
                )
            }
        )
    }
}

@Composable
private fun ProductItem(
    product: Product,
    onProductRemove: (Product) -> Unit,
    onAmountIncrease: (Product) -> Unit,
    onAmountDecrease: (Product) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = product.article.name,
            style = Text.Style(Text.Size.Bold).value,
            modifier = Modifier.weight(25f)
        )
        Spacer(modifier = Modifier.weight(15f))
        Image(
            painter = painterResource(
                if (product.amount > 0) {
                    R.drawable.ic_minus
                } else R.drawable.ic_delete
            ),
            colorFilter = ColorFilter.tint(AppColors.Orange),
            contentDescription = "Button minus",
            modifier = Modifier
                .padding(Plate.padding.dp)
                .clickable {
                    if (product.amount > 0) {
                        onAmountDecrease(product)
                    } else onProductRemove(product)
                }
        )
        Text(
            text = "${product.amount} ${stringResource(R.string.kg)}",
            style = Text.Style(Text.Size.Bold).value,
            modifier = Modifier.weight(20f)
        )
        Image(
            painter = painterResource(R.drawable.ic_plus),
            colorFilter = ColorFilter.tint(AppColors.Orange),
            contentDescription = "Button plus",
            modifier = Modifier
                .padding(Plate.padding.dp)
                .clickable {
                    onAmountIncrease(product)
                }
        )
        Spacer(modifier = Modifier.weight(15f))
        Text(
            text = "${product.price} ${stringResource(R.string.dollar)}",
            style = Text.Style(Text.Size.Bold, AppColors.Orange).value,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(25f)
        )
    }
}