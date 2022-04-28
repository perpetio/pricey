package com.perpetio.pricey.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = DarkThemeColors.Orange,
    secondary = DarkThemeColors.Green,
    surface = DarkThemeColors.Plate,
    background = DarkThemeColors.Background,
    onPrimary = DarkThemeColors.Text,
    onSecondary = DarkThemeColors.Hint,
    onSurface = CommonThemeColors.Icon
)

private val LightColorPalette = lightColors(
    primary = LightThemeColors.Orange,
    secondary = LightThemeColors.Green,
    surface = LightThemeColors.Plate,
    background = LightThemeColors.Background,
    onPrimary = LightThemeColors.Text,
    onSecondary = LightThemeColors.Hint,
    onSurface = CommonThemeColors.Icon
)

@Composable
fun PriceyTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) {
        DarkColorPalette
    } else LightColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(colors.background)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}