package com.perpetio.pricey.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkThemeColors.Orange,
    secondary = DarkThemeColors.Green,
    surface = DarkThemeColors.Plate,
    background = DarkThemeColors.Background,
    onPrimary = DarkThemeColors.Text,
    onSecondary = CommonThemeColors.Hint,
    onSurface = CommonThemeColors.Icon
)

private val LightColorPalette = lightColors(
    primary = LightThemeColors.Orange,
    secondary = LightThemeColors.Green,
    surface = LightThemeColors.Plate,
    background = LightThemeColors.Background,
    onPrimary = LightThemeColors.Text,
    onSecondary = CommonThemeColors.Hint,
    onSurface = CommonThemeColors.Icon
)

@Composable
fun PriceyTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}