package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.*

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ComparisonPage(
        DataProvider.filters,
        true,
        DataProvider.productArticles[0],
        DataProvider.products,
        {},
        {},
        {}
    )
}

@Composable
fun ComparisonPage(
    filters: List<Int>,
    productArticle: ProductArticle,
    products: List<Product>,
    onCheckFilter: (Int) -> Unit,
    onChangeSort: (SortType) -> Unit,
    onAddToBasket: (List<Product>) -> Unit,
    goBack: () -> Unit,
) {
    val selectedProducts = remember {
        mutableStateListOf<Product>()
    }
    var selectedFilter by remember {
        mutableStateOf(filters[0])
    }
    var sortType by remember {
        mutableStateOf(SortType.Descending)
    }
    Column {
        Header(
            productArticle = productArticle,
            goBack = goBack
        )
        Row {
            FilterBar(
                filters = filters,
                selectedFilter = selectedFilter,
                isAscendingSort = sortType,
                onCheckFilter = { filter ->
                    selectedFilter = filter
                    onCheckFilter(filter)
                },
                onChangeSort = {
                    sortType =
                        onChangeSort()
                }
            )
        }
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
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "Button back",
            colorFilter = ColorFilter.tint(AppColors.Orange),
            modifier = Modifier
                .size(ButtonStyle.size.dp)
                .padding(50.dp)
                .clickable { goBack() }
        )
        Image(
            painter = painterResource(
                productArticle.imageResId ?: productArticle.foodCategory.imageResId
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = "Product image"
        )
    }
}

@Composable
private fun FilterBar(
    filters: List<Int>,
    selectedFilter: Int,
    isAscendingSort: Boolean,
    onCheckFilter: (Int) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    val items = remember { filters }
    LazyRow(
        contentPadding = PaddingValues(
            start = Plate.padding.dp,
            top = Plate.padding.dp
        )
    ) {
        items(
            items = items,
            itemContent = { filter ->
                FilterItem(
                    filter = filter,
                    isSelected = (filter == selectedFilter),
                    isAscendingSort = isAscendingSort,
                    onCheck = onCheckFilter,
                    onChangeSort = onChangeSort
                )
            }
        )
    }
}

@Composable
private fun FilterItem(
    filter: Int,
    isSelected: Boolean,
    isAscendingSort: Boolean,
    onCheck: (Int) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    Column(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = {
                if(isSelected) onChangeSort()
                onCheck(filter)
            }
        )
    ) {
        Row {
            Text(
                text = stringResource(filter),
                color = AppColors.DarkGreen
            )
            Spacer(modifier = Modifier.width(IconStyle.padding.dp))
            Image(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = "Filer direction",
                colorFilter = ColorFilter.tint(AppColors.DarkGreen),
                modifier = Modifier
                    .size(IconStyle.size.dp)
                    .rotate(if (isAscendingSort) 180f else 0f)
            )
        }
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .height(LineStyle.size.dp)
                    .fillMaxWidth()
                    .background(AppColors.DarkGreen)
            )
        }
    }
}
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
            start = Plate.padding.dp,
            top = Plate.padding.dp
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
                end = Plate.padding.dp,
                bottom = Plate.padding.dp
            )
            .clickable { onSelect(product) }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp),
        backgroundColor = if (isSelected) Color.DarkGray else MaterialTheme.colors.surface
    ) {
        Text(
            text = product.article.name,
            modifier = Modifier.padding(Plate.padding.dp)
        )
    }
}