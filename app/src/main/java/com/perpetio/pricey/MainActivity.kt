package com.perpetio.pricey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.perpetio.pricey.AppPages.*
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.ui.pages.BasketPage
import com.perpetio.pricey.ui.pages.ComparisonPage
import com.perpetio.pricey.ui.pages.ListPage
import com.perpetio.pricey.ui.theme.PriceyTheme
import com.perpetio.pricey.view_models.BasketViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PriceyTheme {
                SetupPages()
            }
        }
    }
}

@Composable
fun SetupPages(
    basketViewModel: BasketViewModel = BasketViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListPage.name
    ) {
        composable(
            route = ListPage.name
        ) {
            ListPage(
                foodCategories = DataProvider.foodCategories,
                productArticles = DataProvider.productArticles,
                onProductSearch = {},
                onCategorySelect = {},
                onProductSelect = { productName ->
                    navController.navigate("${ComparisonPage.name}/$productName")
                }
            )
        }
        composable(
            route = "${ComparisonPage.name}/{product_name}",
            arguments = listOf(
                navArgument("product_name") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("product_name")?.let { productName ->
                ComparisonPage(
                    products = DataProvider.getProducts(productName),
                    onAddToBasket = { product ->
                        basketViewModel.addToBasket(product)
                    },
                    goToBasket = {
                        navController.navigate(BasketPage.name)
                    }
                )
            }
        }
        composable(
            route = BasketPage.name
        ) {
            BasketPage(
                products = basketViewModel.products,
                onProductRemove = { product ->
                    basketViewModel.removeFromBasket(product)
                },
                onGoBack = {
                    navController.navigate(ListPage.name)
                }
            )
        }
    }
}