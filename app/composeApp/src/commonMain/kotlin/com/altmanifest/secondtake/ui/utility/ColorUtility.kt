package com.altmanifest.secondtake.ui.utility

import androidx.compose.ui.graphics.Color

fun Color.darken(factor: Float = 0.5f): Color {
    return Color(
        red = this.red * factor,
        green = this.green * factor,
        blue = this.blue * factor,
        alpha = this.alpha
    )
}