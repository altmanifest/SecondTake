package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.AlfaSlabOneFontFamily
import com.altmanifest.secondtake.ui.theme.LatoFontFamily
import com.altmanifest.secondtake.ui.theme.TextColor

@Composable
fun CustomHeading(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 40.sp,
        color = TextColor,
        textAlign = TextAlign.Center,
        fontFamily = AlfaSlabOneFontFamily(),
        lineHeight = 55.sp,
        modifier = modifier
            .fillMaxWidth(0.75f)
        )
}

@Composable
fun ComparisonHeading(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 32.sp,
        color = TextColor,
        textAlign = TextAlign.Center,
        fontFamily = LatoFontFamily(),
        modifier = modifier
            .fillMaxWidth(0.75f)
    )
}

@Composable
fun ClickableText(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontFamily = LatoFontFamily(),
        fontSize = 20.sp,
        color = TextColor,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(0.75f),
        textAlign = TextAlign.Center
    )
}