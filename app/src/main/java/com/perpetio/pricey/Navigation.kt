package com.perpetio.pricey.view_models

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.perpetio.pricey.data.*
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
                    filterViewModel.searchOfProductArticles(
                        searchQuery, selectedCategory
                    )
                )
            }
            ListPage(
                searchQuery = searchQuery,
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
                    filterViewModel.productArticle = productArticle
                    navController.navigate(AppPage.ComparisonPage.name)
                }
            )
        }
        composable(
            route = AppPage.ComparisonPage.name
        ) {
            val sortValues = SortValue.values().toList()
            var selectedSortValue by remember {
                mutableStateOf(sortValues[0])
            }
            var selectedSortType by remember {
                mutableStateOf(SortType.Descending)
            }
            val productArticle by remember {
                mutableStateOf(filterViewModel.productArticle)
            }
            val filteredList = filterViewModel.filterProducts(
                productArticle!!,
                filterViewModel.priceFilter,
                filterViewModel.ratingFilter,
                filterViewModel.expirationFilter
            )
            var products by remember {
                mutableStateOf(
                    filterViewModel.sortProducts(
                        filteredList, selectedSortValue, selectedSortType
                    )
                )
            }
            ComparisonPage(
                productArticle = productArticle!!,
                sortValues = sortValues,
                selectedSortValue = selectedSortValue,
                selectedSortType = selectedSortType,
                products = products,
                basketProducts = basketViewModel.basketList,
                onCheckFilter = { sortValue ->
                    selectedSortValue = sortValue
                    products = filterViewModel.sortProducts(
                        products, selectedSortValue, selectedSortType
                    )
                },
                onChangeSort = { sortType ->
                    selectedSortType = sortType
                    products = filterViewModel.sortProducts(
                        products, selectedSortValue, selectedSortType
                    )
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
            var priceFilter by remember {
                mutableStateOf(filterViewModel.priceFilter)
            }
            var ratingFilter by remember {
                mutableStateOf(filterViewModel.ratingFilter)
            }
            var expirationFilter by remember {
                mutableStateOf(filterViewModel.expirationFilter)
            }
            FilterPage(
                productArticle = filterViewModel.productArticle!!,
                priceRange = filterViewModel.getPriceRange(DataProvider.products),
                priceFilter = priceFilter,
                ratingValues = RatingStarts,
                ratingFilter = ratingFilter,
                expirationValues = ExpirationPeriod.values().toList(),
                expirationFilter = expirationFilter,
                onPriceRangeChange = { priceRange ->
                    priceFilter = priceRange
                },
                onRatingChange = { rating ->
                    ratingFilter = rating
                },
                onExpirationPeriodChange = { expirationPeriod ->
                    expirationFilter = expirationPeriod
                },
                onApplyFilters = {
                    filterViewModel.apply {
                        this.priceFilter = priceFilter
                        this.ratingFilter = ratingFilter
                        this.expirationFilter = expirationFilter
                    }
                    navController.popBackStack()
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppPage.BasketPage.name
        ) {
            var basketList by remember {
                mutableStateOf(basketViewModel.basketList)
            }
            BasketPage(
                basketList = basketList,
                onProductRemove = { product ->
                    val copy = basketList.toMutableList()
                    copy.remove(product)
                    basketList = copy
                },
                goBack = {
                    basketViewModel.updateBasketList(basketList)
                    navController.popBackStack()
                }
            )
        }
    }
}
