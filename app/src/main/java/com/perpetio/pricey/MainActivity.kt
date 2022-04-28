package com.perpetio.pricey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.perpetio.pricey.ui.pages.AppPage
import com.perpetio.pricey.ui.theme.Dimen
import com.perpetio.pricey.ui.theme.PriceyTheme
import com.perpetio.pricey.view_models.BasketViewModel
import com.perpetio.pricey.view_models.FilterViewModel
import com.perpetio.pricey.view_models.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PriceyTheme {
                AppUi()
            }
        }
    }
}

@Composable
private fun AppUi(
    filterViewModel: FilterViewModel = FilterViewModel(),
    basketViewModel: BasketViewModel = BasketViewModel()
) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentPage = AppPage.fromRoute(backstackEntry.value?.destination?.route)

    Scaffold(
        bottomBar = {
            BottomBar(
                allPages = AppPage.values().toList(),
                onTabSelected = { page ->
                    navController.navigate(page.name)
                },
                currentPage = currentPage
            )
        }
    ) { innerPadding ->
        NavigationHost(
            filterViewModel = filterViewModel,
            basketViewModel = basketViewModel,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomBar(
    allPages: List<AppPage>,
    onTabSelected: (AppPage) -> Unit,
    currentPage: AppPage
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(
            modifier = Modifier.height(Dimen.Size.line)
        )
        Row(
            modifier = Modifier.height(Dimen.Size.bottomBar),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            allPages.forEach { page ->
                page.iconResId?.let { iconResId ->
                    TabItem(
                        iconResId = iconResId,
                        onSelected = { onTabSelected(page) },
                        selected = (currentPage == page)
                    )
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    iconResId: Int,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val durationMillis = if (selected) 150 else 100
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colors.primary
        } else MaterialTheme.colors.onSurface,
        animationSpec = animSpec
    )
    IconButton(
        modifier = Modifier
            .size(Dimen.Size.button),
        onClick = { onSelected() }
    ) {
        Image(
            painter = painterResource(iconResId),
            colorFilter = ColorFilter.tint(tabTintColor),
            contentDescription = "Tab icon",
            modifier = Modifier.size(Dimen.Size.iconBig)
        )
    }
}