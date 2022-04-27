package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.RatingStarts
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.data.SortValue
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.common.ProductHeader
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.Dimen
import com.perpetio.pricey.ui.theme.Text
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ComparisonPage(
    productArticle: ProductArticle,
    sortValues: List<SortValue>,
    selectedSortValue: SortValue,
    selectedSortType: SortType,
    products: List<Product>,
    basketProducts: List<Product>,
    onCheckFilter: (SortValue) -> Unit,
    onChangeSort: (SortType) -> Unit,
    onUpdateBasket: (List<Product>) -> Unit,
    onOpenFilter: () -> Unit,
    goBack: () -> Unit,
) {
    var isSelectionMode by remember {
        mutableStateOf(false)
    }
    val selectedProducts = remember {
        mutableStateListOf<Product>()
    }
    Column(
        Modifier.background(
            color = AppColors.LightOrange
        )
    ) {
        ProductHeader(
            productArticle = productArticle,
            goBack = goBack
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Space.max)
        ) {
            SortBar(
                sortValues = sortValues,
                selectedSortValue = selectedSortValue,
                selectedSortType = selectedSortType,
                onCheckFilter = onCheckFilter,
                onChangeSort = onChangeSort
            )
            Spacer(modifier = Modifier.weight(1f))
            FilterButton(
                isSelectionMode = isSelectionMode,
                onOpenFilter = onOpenFilter,
                onAddToBasket = {
                    isSelectionMode = false
                    onUpdateBasket(selectedProducts)
                    selectedProducts.clear()
                }
            )
        }
        ComparisonList(
            products = products,
            selectedProducts = selectedProducts,
            basketProducts = basketProducts,
            onProductSelect = { product ->
                isSelectionMode = true
                selectedProducts.apply {
                    if (contains(product)) {
                        remove(product)
                    } else add(product)
                }
            }
        )
    }
}

@Composable
private fun SortBar(
    sortValues: List<SortValue>,
    selectedSortValue: SortValue,
    selectedSortType: SortType,
    onCheckFilter: (SortValue) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            items = sortValues,
            itemContent = { sortValue ->
                SortItem(
                    sortValue = sortValue,
                    sortType = selectedSortType,
                    isSelected = (sortValue == selectedSortValue),
                    onCheck = onCheckFilter,
                    onChangeSort = onChangeSort
                )
            }
        )
    }
}

@Composable
private fun FilterButton(
    isSelectionMode: Boolean,
    onOpenFilter: () -> Unit,
    onAddToBasket: () -> Unit,
) {
    Surface(
        color = AppColors.Orange,
        shape = RoundedCornerShape(Dimen.Corners.main),
        modifier = Modifier
            .size(Dimen.Size.buttonBig)
            .clickable {
                if (isSelectionMode) {
                    onAddToBasket()
                } else onOpenFilter()
            }
    ) {
        Image(
            painter = painterResource(
                if (isSelectionMode) R.drawable.ic_basket else R.drawable.ic_filter
            ),
            contentDescription = "Button back",
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimen.Padding.main)
        )
    }
}

@Composable
private fun SortItem(
    sortValue: SortValue,
    sortType: SortType,
    isSelected: Boolean,
    onCheck: (SortValue) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(end = Dimen.Space.max)
            .selectable(
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        onChangeSort(sortType.getOpposite())
                    } else onCheck(sortValue)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(sortValue.resId),
                color = AppColors.DarkGreen
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(Dimen.Space.small))
                Image(
                    painter = painterResource(R.drawable.ic_arrow_up),
                    contentDescription = "Filer direction",
                    colorFilter = ColorFilter.tint(AppColors.DarkGreen),
                    modifier = Modifier
                        .size(Dimen.Size.icon)
                        .rotate(if (sortType == SortType.Ascending) 180f else 0f)
                )
            }
        }
        if (isSelected) {
            Spacer(modifier = Modifier.height(Dimen.Space.small))
            Divider(
                color = AppColors.DarkGreen,
                thickness = Dimen.Size.lineBold
            )
        }
    }
}

@Composable
private fun ComparisonList(
    products: List<Product>,
    selectedProducts: List<Product>,
    basketProducts: List<Product>,
    onProductSelect: (Product) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = Dimen.Padding.main
        )
    ) {
        items(
            items = products,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    isSelected = selectedProducts.contains(product),
                    isInBasket = basketProducts.contains(product),
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
    isInBasket: Boolean,
    onSelect: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = Dimen.Space.main)
            .clickable { onSelect(product) }
            .fillMaxWidth(),
        elevation = Dimen.Elevation.main,
        shape = RoundedCornerShape(Dimen.Corners.main),
        border = if (isSelected) {
            BorderStroke(Dimen.Size.lineBold, AppColors.Orange)
        } else null
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimen.Padding.max,
                        vertical = Dimen.Padding.main
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(product.store.chain.imageResId),
                        contentDescription = "Store chain image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(Dimen.Size.storeImage)
                    )
                    Spacer(modifier = Modifier.height(Dimen.Space.small))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            colorFilter = ColorFilter.tint(AppColors.Orange),
                            contentDescription = "Store chain image",
                            modifier = Modifier.height(Dimen.Size.icon)
                        )
                        Text(
                            text = "${product.store.remoteness} ${stringResource(R.string.km)}",
                            style = Text.Style(Text.Size.Small).value,
                            modifier = Modifier.padding(start = Dimen.Space.small)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${product.price} ${stringResource(R.string.dollar)}",
                        style = Text.Style(Text.Size.Max, AppColors.Orange).value
                    )
                    Spacer(modifier = Modifier.height(Dimen.Space.small))
                    Text(
                        text = "(${product.amount} ${stringResource(R.string.kg)})",
                        style = Text.Style(Text.Size.Small).value
                    )
                }
                Column {
                    Rating(
                        selectedValue = product.rating,
                        rangeValues = RatingStarts
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    Text(
                        text = "Exp: ${dateFormat.format(product.expirationDate)}",
                        style = Text.Style(Text.Size.Small).value
                    )
                }
            }
            if (isInBasket) {
                Surface(
                    color = AppColors.Orange,
                    shape = RoundedCornerShape(
                        topEnd = Dimen.Corners.main,
                        bottomStart = Dimen.Corners.main
                    ),
                    modifier = Modifier
                        .size(Dimen.Size.marker)
                        .align(Alignment.TopEnd)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_basket),
                        contentDescription = "Basket mark",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(7.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Rating(
    selectedValue: Int,
    rangeValues: IntRange
) {
    Row {
        for (value in rangeValues) {
            Image(
                painter = painterResource(
                    if (value <= selectedValue) R.drawable.ic_start
                    else R.drawable.ic_unstart
                ),
                colorFilter = ColorFilter.tint(AppColors.Orange),
                contentDescription = "Rating star",
                modifier = Modifier
                    .height(Dimen.Size.icon)
                    .padding(end = Dimen.Space.main)
            )
        }
    }
}