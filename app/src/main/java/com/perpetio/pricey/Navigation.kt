package com.perpetio.pricey.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
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
                    filterViewModel.selectedArticle.value = productArticle
                    navController.navigate(AppPage.ComparisonPage.name)
                }
            )
        }
        composable(
            route = AppPage.ComparisonPage.name
        ) {
            val productArticle = filterViewModel.selectedArticle.value!!
            val filters = Filter.values().toList()
            val selectedFilter by remember {
                mutableStateOf(Filter.resIds[0])
            }
            val sortType by remember {
                mutableStateOf(Filter.SortType.Descending)
            }
            val products = filterViewModel.getProducts(
                productArticle, selectedFilter, sortType
            )
            ComparisonPage(
                productArticle = productArticle,
                products = DataProvider.getProducts(productArticle.name),
                onAddToBasket = { product ->
                    basketViewModel.addToBasket(product)
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
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
