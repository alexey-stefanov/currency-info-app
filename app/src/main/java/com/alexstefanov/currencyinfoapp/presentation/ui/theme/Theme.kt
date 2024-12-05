package com.alexstefanov.currencyinfoapp.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = LightPurple,
    tertiary = Yellow,
    background = Color.White,
    surface = LightLightGray,
    surfaceVariant = LightGrayBlue,
    surfaceContainer = Color.White,
    secondaryContainer = LightBlue,
    onSecondaryContainer = DarkBlue,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkGray,
    onSurface = DarkGray,
    onSurfaceVariant = LightPurple,
    outline = LightGray,
    error = Red
)

@Composable
fun CurrencyInfoAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}