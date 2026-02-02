package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.PrimaryButtonColor
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.FilmWebButtonColor
import com.altmanifest.secondtake.ui.theme.FilmwebFontShadowColor
import com.altmanifest.secondtake.ui.theme.IMDBButtonColor
import com.altmanifest.secondtake.ui.theme.IMDBFontShadowColor
import com.altmanifest.secondtake.ui.theme.LatoFontFamily
import com.altmanifest.secondtake.ui.theme.OnlyfilmsButtonColor
import com.altmanifest.secondtake.ui.theme.OnlyfilmsFontShadowColor
import com.altmanifest.secondtake.ui.theme.PrimaryDisabledFontShadowColor
import com.altmanifest.secondtake.ui.theme.PrimaryFontShadowColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Filmweb
import secondtake.composeapp.generated.resources.IMDB_Logo_2016
import secondtake.composeapp.generated.resources.Onlyfilms
import secondtake.composeapp.generated.resources.Res

@Composable
fun CustomButton(text: String, enabled: Boolean = true, onClick: () -> Unit, isLoading: Boolean = false) {
    val currentFontShadowColor = if (enabled && !isLoading) PrimaryFontShadowColor else PrimaryDisabledFontShadowColor

    Button(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        colors = PrimaryButtonColor,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .height(80.dp)
            .width(296.dp)
            .drawBehind {
            // Create a hard shadow matching box-shadow: 0 6px 0 0
            drawRoundRect(
                color = currentFontShadowColor,
                topLeft = Offset(0f, 6.dp.toPx()), // Vertical offset
                size = size,
                cornerRadius = CornerRadius(20.dp.toPx()) // Should match the Button's shape
            )}
        ) {
        if (!isLoading) {
            Text(
                text = text,
                fontSize = 32.sp,
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp).height(48.dp),
                currentFontShadowColor
            )
        }
    }
}

@Composable
fun IMDBButton(enabled: Boolean = true, onClick: () -> Unit, isLoading: Boolean = false) {
    val currentFontShadowColor = if (enabled && !isLoading) IMDBFontShadowColor else PrimaryDisabledFontShadowColor

    Button(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        colors = IMDBButtonColor,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .width(296.dp)
            .height(80.dp)
            .drawBehind {
            // Create a hard shadow matching box-shadow: 0 6px 0 0
            drawRoundRect(
                color = currentFontShadowColor,
                topLeft = Offset(0f, 6.dp.toPx()), // Vertical offset
                size = size,
                cornerRadius = CornerRadius(20.dp.toPx()) // Should match the Button's shape
            )}
    ) {
        if (!isLoading) {
            Icon(
                painter = painterResource(Res.drawable.IMDB_Logo_2016),
                contentDescription = null,
                Modifier.height(30.dp).width(74.dp)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp).height(48.dp),
                currentFontShadowColor
            )
        }
    }
}

@Composable
fun FilmwebButton(enabled: Boolean = true, onClick: () -> Unit, isLoading: Boolean = false) {
    val currentFontShadowColor = if (enabled && !isLoading) FilmwebFontShadowColor else PrimaryDisabledFontShadowColor

    Button(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        colors = FilmWebButtonColor,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .width(296.dp)
            .height(80.dp)
            .drawBehind {
                // Create a hard shadow matching box-shadow: 0 6px 0 0
                drawRoundRect(
                    color = currentFontShadowColor,
                    topLeft = Offset(0f, 6.dp.toPx()), // Vertical offset
                    size = size,
                    cornerRadius = CornerRadius(20.dp.toPx()) // Should match the Button's shape
                )}
    ) {
        if (!isLoading) {
            Image(
                painter = painterResource(Res.drawable.Filmweb),
                contentDescription = null,
                modifier = Modifier
                    .height(32.dp)
                    .width(130.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(currentFontShadowColor)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp).height(48.dp),
                color = currentFontShadowColor
            )
        }
    }
}
@Composable
fun OnlyFilmsButton(enabled: Boolean = true, onClick: () -> Unit, isLoading: Boolean = false) {
    val currentFontShadowColor = if (enabled && !isLoading) OnlyfilmsFontShadowColor else PrimaryDisabledFontShadowColor

    Button(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        colors = OnlyfilmsButtonColor,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .width(296.dp)
            .height(80.dp)
            .drawBehind {
                drawRoundRect(
                    color = currentFontShadowColor,
                    topLeft = Offset(0f, 6.dp.toPx()), // Vertical offset
                    size = size,
                    cornerRadius = CornerRadius(20.dp.toPx()) // Should match the Button's shape
                )}
    ) {
        if (!isLoading) {
            Icon(
                painter = painterResource(Res.drawable.Onlyfilms),
                contentDescription = null,
                Modifier
                    .padding(end = 2.dp)
                    .width(48.dp)
                    .height(48.dp)
            )
            Text(text = "OnlyFilms",
                fontFamily = LatoFontFamily(),
                fontSize = 26.sp,
                fontWeight = FontWeight(700)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp).height(48.dp),
                color = currentFontShadowColor
            )
        }
    }
}