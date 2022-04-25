package com.perpetio.pricey.view_models

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.Filter
import com.perpetio.pricey.data.SortType
import com.perpetio.pricey.ui.pages.*

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
            var searchQuery by remember {
                mutableStateOf("")
            }
            var selectedCategory by remember {
                mutableStateOf(DataProvider.foodCategories[0])
            }
            var productArticles by remember {
                mutableStateOf(
                    filterViewModel.searchOfProductArticles(searchQuery, selectedCategory)
                )
            }
            ListPage(
                foodCategories = DataProvider.foodCategories,
                selectedCategory = selectedCategory,
                productArticles = productArticles,
                onSearchChange = { query ->
                    searchQuery = query
                    productArticles = filterViewModel.searchOfProductArticles(
                        searchQuery, selectedCategory
                    )
                },
                onCategoryChange = { foodCategory ->
                    selectedCategory = foodCategory
                    productArticles = filterViewModel.searchOfProductArticles(
                        searchQuery, selectedCategory
                    )
                },
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
                mutableStateOf(filterViewModel.filterProducts(selectedFilter, selectedSort))
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
                    products = filterViewModel.filterProducts(selectedFilter, selectedSort)
                },
                onChangeSort = { sortType ->
                    selectedSort = sortType
                    products = filterViewModel.filterProducts(selectedFilter, selectedSort)
                },
                onUpdateBasket = { newProducts ->
                    basketViewModel.addToBasket(newProducts)
                },
                onOpenFilter = {
                    navController.navigate(AppPage.FilterPage.name)
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppPage.FilterPage.name
        ) {
            FilterPage(
                productArticle = filterViewModel.productArticle.value!!,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppPage.BasketPage.name
        ) {
            val basketList by remember {
                mutableStateOf(basketViewModel.basketList)
            }
            BasketPage(
                basketList = basketList,
                onProductRemove = {
                    //basketViewModel.removeFromList(listOf(product)) // todo
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
