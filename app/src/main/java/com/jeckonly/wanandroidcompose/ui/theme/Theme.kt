package com.jeckonly.wanandroidcompose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Blue5,
    primaryVariant = Blue5,
    secondary = Blue10,
    secondaryVariant = Blue10,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Blue5,
    onSurface = Blue5,
)

@Composable
fun WanandroidComposeTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}