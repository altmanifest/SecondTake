package com.altmanifest.secondtake.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import secondtake.composeapp.generated.resources.AlfaSlabOne_Regular
import secondtake.composeapp.generated.resources.Lato_Bold
import secondtake.composeapp.generated.resources.Lato_Italic
import secondtake.composeapp.generated.resources.Lato_Regular
import secondtake.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AlfaSlabOneFontFamily() = FontFamily(
    Font(Res.font.AlfaSlabOne_Regular, FontWeight.Normal)
)

@Composable
fun LatoFontFamily() = FontFamily(
    Font(Res.font.Lato_Regular, FontWeight.Normal),
    Font(Res.font.Lato_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.Lato_Bold, FontWeight.Bold),
)

@Composable
fun AppTypography() = Typography().run {
    val alfaSlabOne = AlfaSlabOneFontFamily()
    val lato = LatoFontFamily()

    copy(
        //Alfa Slab One for Labels and Headings
        displayLarge = displayLarge.copy(fontFamily = alfaSlabOne),
        displayMedium = displayMedium.copy(fontFamily = alfaSlabOne),
        displaySmall = displaySmall.copy(fontFamily = alfaSlabOne),
        headlineLarge = headlineLarge.copy(fontFamily = alfaSlabOne),
        headlineMedium = headlineMedium.copy(fontFamily = alfaSlabOne),
        headlineSmall = headlineSmall.copy(fontFamily = alfaSlabOne),
        titleLarge = titleLarge.copy(fontFamily = alfaSlabOne),
        titleMedium = titleMedium.copy(fontFamily = alfaSlabOne),
        titleSmall = titleSmall.copy(fontFamily = alfaSlabOne),

        labelLarge = labelLarge.copy(fontFamily = alfaSlabOne),
        labelMedium = labelMedium.copy(fontFamily = alfaSlabOne),
        labelSmall = labelSmall.copy(fontFamily = alfaSlabOne),

        //Lato for the rest
        bodyLarge = bodyLarge.copy(fontFamily = lato),
        bodyMedium = bodyMedium.copy(fontFamily = lato),
        bodySmall = bodySmall.copy(fontFamily = lato)
    )
}