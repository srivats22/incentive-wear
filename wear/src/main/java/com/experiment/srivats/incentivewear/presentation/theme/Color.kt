package com.experiment.srivats.incentivewear.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val TealPrimary = Color(0xFF006d77)
val TealSecondary = Color(0xFF83c5be)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette: Colors = Colors(
    primary = TealPrimary,
    primaryVariant = TealPrimary,
    secondary = TealSecondary,
    secondaryVariant = Teal200,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)