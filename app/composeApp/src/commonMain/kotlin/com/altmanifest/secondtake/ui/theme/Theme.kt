package com.altmanifest.secondtake.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun SecondTakeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = darkColorScheme(background = BackgroundColor),
        typography = AppTypography(),
        content = content
    )
}

