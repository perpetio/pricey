package com.perpetio.pricey.ui.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.common.BackButton
import com.perpetio.pricey.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*


@Composable
private fun Preview() {
    val filters = Filter.values().toList()
    ComparisonPage(
        DataProvider.productArticles[0],
        filters,
        filters[0],
        SortType.Descending,
        listOf(),
        DataProvider.products,
        {},
        {},
        {},
        {},
        {}
    )
}

@Composable
fun ComparisonPage(
    productArticle: ProductArticle,
    filters: List<Filter>,
    selectedFilter: Filter,
    sortType: SortType,
    products: List<Product>,
    basketProducts: List<Product>,
    onCheckFilter: (Filter) -> Unit,
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
        Header(
            productArticle = productArticle,
            goBack = goBack
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            FilterBar(
                filters = filters,
                selectedFilter = selectedFilter,
                sortType = sortType,
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
                    if(contains(product)) {
                        remove(product)
                    } else add(product)
                }
            }
        )
    }
}

@Composable
private fun Header(
    productArticle: ProductArticle,
    goBack: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(
            bottomStart = Plate.corners.dp,
            bottomEnd = Plate.corners.dp
        ),
        elevation = Plate.elevation.dp
    ) {
        Box(
            modifier = Modifier.padding(SpaceStyle.main.dp),
        ) {
            BackButton(
                modifier = Modifier.align(Alignment.TopStart),
                goBack = goBack
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(productArticle.imageResId),
                    modifier = Modifier
                        .padding(bottom = SpaceStyle.main.dp)
                        .fillMaxWidth()
                        .height(150.dp),
                    contentDescription = "Product image"
                )
                Text(
                    text = productArticle.name,
                    style = Text.Style(Text.Size.Max, AppColors.Orange).value
                )
            }
        }
    }
}

@Composable
private fun FilterBar(
    filters: List<Filter>,
    selectedFilter: Filter,
    sortType: SortType,
    onCheckFilter: (Filter) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    val items = remember { filters }
    LazyRow(
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            items = items,
            itemContent = { filter ->
                FilterItem(
                    filter = filter,
                    sortType = sortType,
                    isSelected = (filter == selectedFilter),
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
        shape = RoundedCornerShape(Plate.corners.dp),
        modifier = Modifier
            .size(ButtonStyle.size.dp)
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
                .padding(ButtonStyle.padding.dp)
        )
    }
}

@Composable
private fun FilterItem(
    filter: Filter,
    sortType: SortType,
    isSelected: Boolean,
    onCheck: (Filter) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(end = 20.dp)
            .selectable(
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        onChangeSort(sortType.getOpposite())
                    } else onCheck(filter)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(filter.resId),
                color = AppColors.DarkGreen
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(IconStyle.padding.dp))
                Image(
                    painter = painterResource(R.drawable.ic_arrow_up),
                    contentDescription = "Filer direction",
                    colorFilter = ColorFilter.tint(AppColors.DarkGreen),
                    modifier = Modifier
                        .size(IconStyle.size.dp)
                        .rotate(if (sortType == SortType.Ascending) 180f else 0f)
                )
            }
        }
        if (isSelected) {
            Spacer(modifier = Modifier.height(IconStyle.padding.dp))
            Divider(
                color = AppColors.DarkGreen,
                thickness = LineStyle.size.dp
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
    val items = remember { products }
    LazyColumn(
        modifier = Modifier.padding(horizontal = Plate.padding.dp)
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
            .padding(bottom = Plate.padding.dp)
            .clickable { onSelect(product) }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp),
        border = if (isSelected) {
            BorderStroke(LineStyle.size.dp, AppColors.Orange)
        } else null
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(product.store.chain.imageResId),
                        contentDescription = "Store chain image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(70.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            colorFilter = ColorFilter.tint(AppColors.Orange),
                            contentDescription = "Store chain image",
                            modifier = Modifier.height(15.dp)
                        )
                        Text(
                            text = "${product.store.remoteness} ${stringResource(R.string.km)}",
                            style = Text.Style(Text.Size.Small).value,
                            modifier = Modifier.padding(start = IconStyle.padding.dp)
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
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "(${product.amount} ${stringResource(R.string.kg)})",
                        style = Text.Style(Text.Size.Small).value
                    )
                }
                Column {
                    Rating(
                        currentValue = product.rating,
                        maxValue = Product.MAX_RATING
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
                        topEnd = Plate.corners.dp,
                        bottomStart = Plate.corners.dp
                    ),
                    modifier = Modifier
                        .size(25.dp)
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
    currentValue: Int,
    maxValue: Int
) {
    Row {
        for (tmpValue in 1..maxValue) {
            Image(
                painter = painterResource(
                    if (tmpValue <= currentValue) R.drawable.ic_start
                    else R.drawable.ic_unstart
                ),
                colorFilter = ColorFilter.tint(AppColors.Orange),
                contentDescription = "Rating star",
                modifier = Modifier
                    .height(12.dp)
                    .padding(end = 10.dp)
            )
        }
    }
}