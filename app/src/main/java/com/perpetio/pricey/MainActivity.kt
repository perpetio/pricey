package com.perpetio.pricey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.perpetio.pricey.ui.pages.AppPage
import com.perpetio.pricey.ui.theme.Animation
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.Plate
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
            modifier = Modifier
                .height(1.dp)
                .background(AppColors.Gray)
        )
        Row(
            modifier = Modifier
                .selectableGroup()
                .height(TabHeight),
            horizontalArrangement = Arrangement.SpaceEvenly
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
    val durationMillis = if (selected) Animation.TabIcon.fadeIn else Animation.TabIcon.fadeOut
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) AppColors.Orange else AppColors.DarkGray,
        animationSpec = animSpec
    )
    Icon(
        painter = painterResource(iconResId),
        contentDescription = "Tab icon",
        tint = tabTintColor,
        modifier = Modifier
            .padding(Plate.padding.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
    )
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f