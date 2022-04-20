package com.perpetio.pricey.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
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
            val productArticle by remember { filterViewModel.productArticle }
            var selectedFilter by remember { filterViewModel.filter }
            var selectedSort by remember { filterViewModel.sortType }
            var products by remember { filterViewModel.products }
            ComparisonPage(
                productArticle = productArticle!!,
                filters = filters,
                selectedFilter = selectedFilter,
                sortType = selectedSort,
                products = products,
                onCheckFilter = { filter ->
                    selectedFilter = filter
                    products = filterViewModel.getProducts()
                },
                onChangeSort = { sortType ->
                    selectedSort = sortType
                    products = filterViewModel.getProducts()
                },
                onAddToBasket = { product ->
                    basketViewModel.addToBasket(product)
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
                products = basketViewModel.products,
                onProductRemove = { product ->
                    basketViewModel.removeFromBasket(product)
                },
                onGoBack = {
                    navController.navigate(AppPage.ListPage.name)
                }
            )
        }
    }
}
