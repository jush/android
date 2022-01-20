package io.homeassistant.companion.android.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors
import androidx.compose.material.darkColors

val Blue = Color(0xFF03A9F4)
val BlueDark = Color(0xFF0288D1)
val Yellow = Color(0xFFFDD835)
val Orange = Color(0xFFFF9800)
val Red = Color(0xFFD32F2F)

internal val wearColorPalette: Colors = darkColors(
    primary = Blue,
    primaryVariant = BlueDark,
    secondary = Yellow,
    secondaryVariant = Orange,
    error = Red,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)
