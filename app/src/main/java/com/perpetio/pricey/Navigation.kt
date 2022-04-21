package com.perpetio.pricey.view_models

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.ui.pages.AppPage
import com.perpetio.pricey.ui.pages.BasketPage
import com.perpetio.pricey.ui.pages.ComparisonPage
import com.perpetio.pricey.ui.pages.ListPage

@Composable
fun NavigationHost(
    filterViewModel: FilterViewModel,
    basketViewModel: BasketViewModel,
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppPage.ListPage.name,
        modifier = modifier
    ) {
        composable(
            route = AppPage.ListPage.name
        ) {
            ListPage(
                foodCategories = DataProvider.foodCategories,
                productArticles = DataProvider.productArticles,
                onProductSearch = {},
                onCategorySelect = {},
                onProductSelect = { productArticle ->
                    filterViewModel.productArticle.value = productArticle
                    navController.navigate(AppPage.ComparisonPage.name)
                }
            )
        }
        composable(
            route = AppPage.ComparisonPage.name
        ) {
            val filters = Filter.values().toList()
            var selectedFilter by remember { mutableStateOf(filters[0]) }
            var selectedSort by remember { mutableStateOf(SortType.Descending) }
            val productArticle by remember { filterViewModel.productArticle }
            var products by remember {
                mutableStateOf(filterViewModel.getProducts(selectedFilter, selectedSort))
            }
            ComparisonPage(
                productArticle = productArticle!!,
                filters = filters,
                selectedFilter = selectedFilter,
                sortType = selectedSort,
                products = products,
                basketProducts = basketViewModel.basketList,
                onCheckFilter = { filter ->
                    selectedFilter = filter
                    products = filterViewModel.getProducts(selectedFilter, selectedSort)
                },
                onChangeSort = { sortType ->
                    selectedSort = sortType
                    products = filterViewModel.getProducts(selectedFilter, selectedSort)
                },
                onUpdateBasket = { newProducts ->
                    basketViewModel.addToBasket(newProducts)
                },
                onOpenFilter = {
                    navController.navigate(AppPage.BasketPage.name) // todo Open filter
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppPage.BasketPage.name
        ) {
            BasketPage(
                basketList = basketViewModel.basketList,
                onProductRemove = { product ->
                    basketViewModel.removeFromList(listOf(product)) // todo
                },
                goBack = {
                    navController.navigate(AppPage.ListPage.name)
                }
            )
        }
    }
}
