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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.models.Product
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.*

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val filters = Filter.values().toList()
    ComparisonPage(
        DataProvider.productArticles[0],
        filters,
        filters[0],
        SortType.Ascending,
        DataProvider.products,
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
    onCheckFilter: (Filter) -> Unit,
    onChangeSort: (SortType) -> Unit,
    onAddToBasket: (List<Product>) -> Unit,
    goBack: () -> Unit,
) {
    val selectedProducts = remember {
        mutableStateListOf<Product>()
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
                sortType = sortType,
                onCheckFilter = onCheckFilter,
                onChangeSort = onChangeSort
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
        Box(
            modifier = Modifier.padding(SpaceStyle.main.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(AppColors.Orange),
                modifier = Modifier
                    .size(ButtonStyle.size.dp)
                    .padding(ButtonStyle.padding.dp)
                    .clickable { goBack() }
                    .align(Alignment.TopStart)
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
                    style = TextStyle(AppColors.Orange).big
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
private fun FilterItem(
    filter: Filter,
    sortType: SortType,
    isSelected: Boolean,
    onCheck: (Filter) -> Unit,
    onChangeSort: (SortType) -> Unit
) {
    Column(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = {
                if (isSelected) {
                    onChangeSort(sortType.getOpposite())
                } else onCheck(filter)
            }
        )
    ) {
        Row {
            Text(
                text = stringResource(filter.resId),
                color = AppColors.DarkGreen
            )
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