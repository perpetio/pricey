package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.models.FoodCategory
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.Input
import com.perpetio.pricey.ui.theme.Plate
import com.perpetio.pricey.ui.theme.TextStyle

@Composable
fun ListPage(
    foodCategories: List<FoodCategory>,
    productArticles: List<ProductArticle>,
    onProductSearch: (String) -> Unit,
    onCategorySelect: (FoodCategory) -> Unit,
    onProductSelect: (String) -> Unit,
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    var selectedCategory by remember {
        mutableStateOf(foodCategories[0])
    }
    Column {
        SearchField(
            searchQuery = searchQuery,
            onSearchChange = { query ->
                searchQuery = query
                onProductSearch(query)
            }
        )
        ListOfCategories(
            foodCategories = foodCategories,
            selectedCategory = selectedCategory,
            onCategorySelect = onCategorySelect
        )
        Text(
            text = stringResource(
                R.string.categories
            )
        )
        ListOfProducts(
            foodCategory = selectedCategory,
            productArticles = productArticles,
            onProductSelect = onProductSelect
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ListPage(
        DataProvider.foodCategories,
        DataProvider.productArticles,
        {},
        {},
        {}
    )
}

@Composable
private fun SearchField(
    searchQuery: String,
    onSearchChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier.padding(Plate.padding.dp),
        color = Color.Transparent
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { onSearchChange(it) },
            textStyle = TextStyle().small,
            singleLine = true,
            label = { Text(stringResource(R.string.search)) },
            modifier = Modifier
                .padding(Plate.padding.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Plate.corners.dp),
            leadingIcon = { if (searchQuery.isEmpty()) SearchIcon() },
            trailingIcon = { if (searchQuery.isNotEmpty()) SearchIcon() }
        )
    }
}

@Composable
private fun SearchIcon() {
    Icon(
        painter = painterResource(R.drawable.ic_search),
        tint = AppColors.Orange,
        contentDescription = "search",
        modifier = Modifier.size(Input.iconSize.dp)
    )
}

@Composable
private fun ListOfCategories(
    foodCategories: List<FoodCategory>,
    selectedCategory: FoodCategory,
    onCategorySelect: (FoodCategory) -> Unit,
) {
    val items = remember { foodCategories }
    LazyRow(
        contentPadding = PaddingValues(
            start = Plate.padding.dp,
            top = Plate.padding.dp,
            bottom = Plate.padding.dp
        )
    ) {
        items(
            items = items,
            itemContent = { category ->
                CategoryItem(
                    category = category,
                    isSelected = (category == selectedCategory),
                    onSelect = onCategorySelect
                )
            }
        )
    }
}

@Composable
private fun CategoryItem(
    category: FoodCategory,
    isSelected: Boolean,
    onSelect: (FoodCategory) -> Unit
) {
    Card(
        border = BorderStroke(Plate.border.dp, AppColors.Orange),
        backgroundColor = if (isSelected) AppColors.Orange else Color.Transparent,
        modifier = Modifier
            .padding(end = Plate.padding.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelect(category) }
            )
    ) {
        Row {
            Image(
                painter = painterResource(category.imageResId),
                contentDescription = "Category image"
            )
            Spacer(modifier = Modifier.width(Plate.padding.dp))
            Text(text = category.name)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListOfProducts(
    foodCategory: FoodCategory,
    productArticles: List<ProductArticle>,
    onProductSelect: (String) -> Unit,
) {
    val items = remember { productArticles }
    Column {
        Text(
            text = foodCategory.name,
            style = TextStyle().title
        )
        Spacer(modifier = Modifier.height(Plate.padding.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = Plate.padding.dp,
                top = Plate.padding.dp
            )
        ) {
            items(
                items = items,
                itemContent = { product ->
                    ProductItem(
                        productArticle = product,
                        onSelect = onProductSelect
                    )
                }
            )
        }
    }
}

@Composable
private fun ProductItem(
    productArticle: ProductArticle,
    onSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = Plate.padding.dp,
                bottom = Plate.padding.dp
            )
            .clickable { onSelect(productArticle.name) }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp)
    ) {
        Column {
            Image(
                painter = painterResource(
                    productArticle.imageResId ?: productArticle.foodCategory.imageResId
                ),
                contentDescription = "Product image",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Text(
                text = productArticle.name,
                modifier = Modifier.padding(Plate.padding.dp)
            )
        }
    }
}