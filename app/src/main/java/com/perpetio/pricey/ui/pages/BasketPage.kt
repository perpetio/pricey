package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.perpetio.pricey.ui.common.BackButton
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.ButtonStyle
import com.perpetio.pricey.ui.theme.SpaceStyle
import com.perpetio.pricey.ui.theme.Text

const val BASE_AMOUNT = 1.0

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    BasketPage(
//        emptyList(),
//        {},
//        {},
//        {},
//        {}
//    )
    ProductItem(
        product = DataProvider.products[0],
        onAmountUpdate = {},
        onProductRemove = {},
    )
}

@Composable
fun BasketPage(
    basketList: List<Product>,
    onProductRemove: () -> Unit,
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
            BackButton(
                modifier = Modifier.align(Alignment.TopStart),
                goBack = goBack
            )
            Text(
                text = stringResource(R.string.cart),
                style = Text.Style(Text.Size.Max, AppColors.Orange).value,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        BasketList(
            basketList = basketList,
            onProductRemove = onProductRemove
        )
    }
}

@Composable
private fun BasketList(
    basketList: List<Product>,
    onProductRemove: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        var store: Store? = null
        var products = mutableListOf<Product>()
        basketList.sortedBy { it.store.chain.name }.forEach { product ->
            if (store != product.store) {
                store?.let {
                    storeItem(
                        store = it,
                        products = products,
                        onProductRemove = onProductRemove,
                        scope = this
                    )
                    products = mutableListOf()
                }
                store = product.store
            }
            products.add(product)
        }
        store?.let {
            storeItem(
                store = it,
                products = products,
                onProductRemove = onProductRemove,
                scope = this
            )
        }
    }
}

private fun storeItem(
    store: Store,
    products: List<Product>,
    onProductRemove: () -> Unit,
    scope: LazyListScope
) {
    var total by mutableStateOf(getTotal(products))
    scope.apply {
        item {
            Spacer(modifier = Modifier.padding(10.dp))
            StoreTitle(store)
            Spacer(modifier = Modifier.height(10.dp))
        }
        products.forEach { product ->
            item {
                ProductItem(
                    product = product,
                    onAmountUpdate = { deltaAmount ->
                        total += deltaAmount * product.price
                    },
                    onProductRemove = onProductRemove,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        item {
            TotalCost(total)
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

private fun getTotal(
    products: List<Product>
): Double {
    var total = 0.0
    products.forEach { product ->
        total += product.price * BASE_AMOUNT
    }
    return total
}

@Composable
private fun StoreTitle(
    store: Store
) {
    Column {
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
    }
}

@Composable
private fun ProductItem(
    product: Product,
    onAmountUpdate: (Int) -> Unit,
    onProductRemove: () -> Unit,
) {
    var amount by remember { mutableStateOf(BASE_AMOUNT) }
    val deltaAmount = 1
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = product.article.name,
            style = Text.Style(Text.Size.Bold).value,
            modifier = Modifier.weight(25f)
        )
        Spacer(modifier = Modifier.weight(10f))
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
                if (amount - deltaAmount < 0) {
                    amount = 0.0
                    onProductRemove()
                } else {
                    amount -= deltaAmount
                    onAmountUpdate(-deltaAmount)
                }
            }
        ) {
            Image(
                painter = painterResource(
                    if (amount > 0) {
                        R.drawable.ic_minus
                    } else R.drawable.ic_delete
                ),
                colorFilter = ColorFilter.tint(AppColors.Orange),
                contentDescription = "Button minus",
                modifier = Modifier.size(
                    if (amount > 0) 15.dp else 20.dp
                )
            )
        }
        Text(
            text = "$amount ${stringResource(R.string.kg)}",
            style = Text.Style(Text.Size.Bold).value,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(30f)
        )
        IconButton(
            modifier = Modifier.size(30.dp),
            enabled = (amount < product.amount),
            onClick = {
                if (amount + deltaAmount > product.amount) {
                    amount = product.amount
                } else {
                    amount += deltaAmount
                    onAmountUpdate(deltaAmount)
                }
            }
        ) {
            Image(
                painter = painterResource(R.drawable.ic_plus),
                colorFilter = ColorFilter.tint(
                    if (amount < product.amount) {
                        AppColors.Orange
                    } else AppColors.Gray
                ),
                contentDescription = "Button plus",
                modifier = Modifier.size(15.dp)
            )
        }
        Spacer(modifier = Modifier.weight(10f))
        Text(
            text = "${amount * product.price} ${stringResource(R.string.dollar)}",
            style = Text.Style(Text.Size.Bold, AppColors.Orange).value,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(25f)
        )
    }
}

@Composable
private fun TotalCost(
    total: Double
) {
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
            text = "$total ${stringResource(R.string.dollar)}",
            style = Text.Style(Text.Size.Title, AppColors.Orange).value,
        )
    }
}