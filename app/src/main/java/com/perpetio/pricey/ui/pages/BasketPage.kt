package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.perpetio.pricey.R
import com.perpetio.pricey.models.BasketProduct
import com.perpetio.pricey.models.Store
import com.perpetio.pricey.ui.common.BackButton
import com.perpetio.pricey.ui.theme.Dimen
import com.perpetio.pricey.ui.theme.Text

@Composable
fun BasketPage(
    basketList: List<BasketProduct>,
    onProductRemove: (BasketProduct) -> Unit,
    goBack: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Space.main),
        ) {
            BackButton(
                modifier = Modifier.align(Alignment.TopStart),
                goBack = goBack
            )
            Text(
                text = stringResource(R.string.cart),
                style = Text.Style(Text.Size.Max, MaterialTheme.colors.primary).value,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(Dimen.Space.main))
        BasketList(
            basketList = basketList,
            onProductRemove = onProductRemove
        )
    }
}

@Composable
private fun BasketList(
    basketList: List<BasketProduct>,
    onProductRemove: (BasketProduct) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = Dimen.Space.max
        )
    ) {
        var store: Store? = null
        var products = mutableListOf<BasketProduct>()
        basketList.sortedWith(
            compareBy(
                { it.store.chain.name },
                { it.store.remoteness }
            )
        ).forEach { product ->
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
    products: List<BasketProduct>,
    onProductRemove: (BasketProduct) -> Unit,
    scope: LazyListScope
) {
    var total by mutableStateOf(getTotal(products))
    scope.apply {
        item {
            Spacer(modifier = Modifier.padding(Dimen.Space.main))
            StoreTitle(store)
            Spacer(modifier = Modifier.height(Dimen.Space.main))
        }
        products.forEach { product ->
            var basketAmount by mutableStateOf(product.basketAmount)
            item {
                ProductItem(
                    product = product,
                    basketAmount = basketAmount,
                    onAmountUpdate = { newAmount ->
                        total -= basketAmount * product.price
                        total += newAmount * product.price
                        basketAmount = newAmount
                        product.basketAmount = newAmount
                    },
                    onProductRemove = onProductRemove
                )
                Spacer(modifier = Modifier.height(Dimen.Space.main))
            }
        }
        item {
            TotalCost(total)
            Spacer(modifier = Modifier.padding(Dimen.Space.main))
        }
    }
}

private fun getTotal(
    products: List<BasketProduct>
): Double {
    var total = 0.0
    products.forEach { product ->
        product.apply {
            total += price * basketAmount
        }
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
                modifier = Modifier.width(Dimen.Size.storeImage)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${store.remoteness} ${stringResource(R.string.km)}",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.ExtraBold
                ).value
            )
        }
        Divider(
            color = MaterialTheme.colors.onSecondary,
            thickness = Dimen.Size.line
        )
    }
}

@Composable
private fun ProductItem(
    product: BasketProduct,
    basketAmount: Double,
    onAmountUpdate: (Double) -> Unit,
    onProductRemove: (BasketProduct) -> Unit,
) {
    val deltaAmount = 1
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = product.article.name,
            style = Text.Style(
                textSize = Text.Size.Bold,
                color = MaterialTheme.colors.onPrimary
            ).value,
            modifier = Modifier.weight(25f)
        )
        Spacer(modifier = Modifier.weight(10f))
        IconButton(
            modifier = Modifier.size(Dimen.Size.button),
            onClick = {
                if (basketAmount == 0.0) {
                    onProductRemove(product)
                } else if (basketAmount - deltaAmount < 0) {
                    onAmountUpdate(0.0)
                } else onAmountUpdate(basketAmount - deltaAmount)
            }
        ) {
            Image(
                painter = painterResource(
                    if (basketAmount > 0) {
                        R.drawable.ic_minus
                    } else R.drawable.ic_delete
                ),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentDescription = "Button minus",
                modifier = Modifier.size(
                    if (basketAmount > 0) {
                        Dimen.Size.icon
                    } else Dimen.Size.iconBig
                )
            )
        }
        Text(
            text = "$basketAmount ${stringResource(R.string.kg)}",
            style = Text.Style(
                textSize = Text.Size.Bold,
                color = MaterialTheme.colors.onPrimary
            ).value,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(30f)
        )
        IconButton(
            modifier = Modifier.size(Dimen.Size.button),
            enabled = (basketAmount < product.amount),
            onClick = {
                if (basketAmount + deltaAmount > product.amount) {
                    onAmountUpdate(product.amount)
                } else onAmountUpdate(basketAmount + deltaAmount)
            }
        ) {
            Image(
                painter = painterResource(R.drawable.ic_plus),
                colorFilter = ColorFilter.tint(
                    if (basketAmount < product.amount) {
                        MaterialTheme.colors.primary
                    } else MaterialTheme.colors.onSecondary
                ),
                contentDescription = "Button plus",
                modifier = Modifier.size(Dimen.Size.icon)
            )
        }
        Spacer(modifier = Modifier.weight(10f))
        Text(
            text = "${basketAmount * product.price} ${stringResource(R.string.dollar)}",
            style = Text.Style(Text.Size.Bold, MaterialTheme.colors.primary).value,
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
            style = Text.Style(
                textSize = Text.Size.Main,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.ExtraBold
            ).value,
        )
        Spacer(modifier = Modifier.width(Dimen.Space.main))
        Text(
            text = "$total ${stringResource(R.string.dollar)}",
            style = Text.Style(Text.Size.Title, MaterialTheme.colors.primary).value,
        )
    }
}