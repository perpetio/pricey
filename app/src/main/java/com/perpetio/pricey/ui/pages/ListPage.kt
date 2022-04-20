package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.models.FoodCategory
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.*

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
fun ListPage(
    foodCategories: List<FoodCategory>,
    productArticles: List<ProductArticle>,
    onProductSearch: (String) -> Unit,
    onCategorySelect: (FoodCategory) -> Unit,
    onProductSelect: (ProductArticle) -> Unit,
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    var selectedCategory by remember {
        mutableStateOf(foodCategories[0])
    }
    Column(
        Modifier.background(
            color = AppColors.LightOrange
        )
    ) {
        SearchField(
            searchQuery = searchQuery,
            onSearchChange = { query ->
                searchQuery = query
                onProductSearch(query)
            }
        )
        Text(
            stringResource(R.string.categories),
            style = TextStyle(AppColors.DarkGreen).main,
            modifier = Modifier.padding(Plate.padding.dp)
        )
        ListOfCategories(
            foodCategories = foodCategories,
            selectedCategory = selectedCategory,
            onCategorySelect = { foodCategory ->
                selectedCategory = foodCategory
                onCategorySelect(foodCategory)
            }
        )
        Text(
            text = selectedCategory.name,
            style = TextStyle(AppColors.DarkGreen).title,
            modifier = Modifier.padding(Plate.padding.dp)
        )
        ListOfProducts(
            productArticles = productArticles,
            onProductSelect = onProductSelect
        )
    }
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
            label = {
                Text(
                    text = stringResource(R.string.search),
                    style = TextStyle(AppColors.Gray).main
                )
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Plate.corners.dp),
            colors = outlinedTextFieldColors(
                unfocusedBorderColor = AppColors.Orange,
                focusedBorderColor = AppColors.Orange
            ),
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
        modifier = Modifier.size(Icon.size.dp)
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
        shape = RoundedCornerShape(Plate.corners.dp),
        modifier = Modifier
            .padding(end = Plate.padding.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelect(category) }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = CheckedItem.paddingHorizontal.dp,
                vertical = CheckedItem.paddingVertical.dp
            )
        ) {
            Image(
                painter = painterResource(category.imageResId),
                contentDescription = "Category image",
                colorFilter = ColorFilter.tint(
                    if (isSelected) Color.White else AppColors.Orange
                )
            )
            Spacer(modifier = Modifier.width(Icon.padding.dp))
            Text(
                text = category.name,
                color = if (isSelected) Color.White else AppColors.Orange
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListOfProducts(
    productArticles: List<ProductArticle>,
    onProductSelect: (ProductArticle) -> Unit,
) {
    val items = remember { productArticles }
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = Plate.padding.dp
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

@Composable
private fun ProductItem(
    productArticle: ProductArticle,
    onSelect: (ProductArticle) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                end = Plate.padding.dp,
                bottom = Plate.padding.dp
            )
            .clickable { onSelect(productArticle) }
            .fillMaxWidth(),
        elevation = Plate.elevation.dp,
        shape = RoundedCornerShape(Plate.corners.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Plate.padding.dp)
        ) {
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
                style = TextStyle(AppColors.Orange).title,
                modifier = Modifier.padding(top = Plate.padding.dp)
            )
        }
    }
}