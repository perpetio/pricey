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
import com.perpetio.pricey.AppPages.ComparisonPage
import com.perpetio.pricey.AppPages.ListPage
import com.perpetio.pricey.mocks.DataProvider
import com.perpetio.pricey.ui.pages.ComparisonPage
import com.perpetio.pricey.ui.pages.ListPage
import com.perpetio.pricey.ui.theme.PriceyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PriceyTheme {
                InitNavigation()
            }
        }
    }
}

@Composable
fun InitNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ListPage.name) {
        composable(ListPage.name) {
            ListPage(
                onProductClick = { productName ->
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
                val product = DataProvider.getProducts(productName)
                ComparisonPage(product)
            }
        }
    }
}