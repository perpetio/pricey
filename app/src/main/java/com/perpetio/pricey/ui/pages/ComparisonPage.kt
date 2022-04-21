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
import androidx.compose.material.*
import androidx.compose.runtime.*
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
        SortType.Descending,
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
    onCheckFilter: (Filter) -> Unit,
    onChangeSort: (SortType) -> Unit,
    onAddToBasket: (List<Product>) -> Unit,
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
                    onAddToBasket(selectedProducts)
                }
            )
        }
        ComparisonList(
            products = products,
            selectedProducts = selectedProducts,
            onProductSelect = { product ->
                isSelectionMode = true
                if (selectedProducts.contains(product)) {
                    selectedProducts.remove(product)
                } else selectedProducts.add(product)
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
    val passiveColor = MaterialTheme.colors.surface
    val activeColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    Card(
        modifier = Modifier
            .padding(bottom = Plate.padding.dp)
            .clickable { onSelect(product) }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp),
        backgroundColor = if (isSelected) activeColor else passiveColor
    ) {
        Text(
            text = product.article.name,
            modifier = Modifier.padding(Plate.padding.dp)
        )
    }
}