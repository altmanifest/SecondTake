package com.altmanifest.secondtake.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.PrimaryButtonColor
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.FilmWebButtonColor
import com.altmanifest.secondtake.ui.theme.IMDBButtonColor
import com.altmanifest.secondtake.ui.theme.LatoFontFamily
import com.altmanifest.secondtake.ui.theme.OnlyfilmsButtonColor
import com.altmanifest.secondtake.ui.utility.darken
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Filmweb
import secondtake.composeapp.generated.resources.IMDB_Logo_2016
import secondtake.composeapp.generated.resources.Onlyfilms
import secondtake.composeapp.generated.resources.Res

@Composable
fun BaseButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    buttonColor: ButtonColors = PrimaryButtonColor,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
    ) {
    //declaration for press-animation
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressOffset by animateDpAsState(
        targetValue = if (isPressed) 6.dp else 0.dp,
        label = "buttonPressAnimation"
    )

    val isButtonEnabled = enabled && !isLoading

    Button(
        onClick = onClick,
        enabled = isButtonEnabled,
        colors = buttonColor,
        shape = RoundedCornerShape(20),
        interactionSource = interactionSource,
        modifier = modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .height(80.dp)
            .graphicsLayer {
                translationY = pressOffset.toPx()
            }
            .drawBehind {
                //Calculate shadow offset
                // If button pressed -> minimize shadow offset
                val currentShadowOffset = 6.dp.toPx() - pressOffset.toPx()

                drawRoundRect(
                    color = if (isButtonEnabled) {
                        buttonColor.containerColor.darken(0.6f)
                    } else {
                        buttonColor.disabledContainerColor.darken(0.6f)
                    },
                    topLeft = Offset(0f, currentShadowOffset),
                    size = size,
                    cornerRadius = CornerRadius(20.dp.toPx())
                )
            }
        ) {
        if (!isLoading) {
            content()
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp).height(48.dp),
                color = if(isButtonEnabled) buttonColor.contentColor else buttonColor.disabledContentColor
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier) {
    BaseButton(
        enabled = enabled,
        onClick = onClick,
        isLoading = isLoading,
        buttonColor = PrimaryButtonColor,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
        )
    }
}

@Composable
fun IMDBButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    val icon = painterResource(Res.drawable.IMDB_Logo_2016)

    BaseButton(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        buttonColor = IMDBButtonColor,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            Modifier
                .fillMaxHeight(0.63f)
                .aspectRatio(icon.intrinsicSize.width / icon.intrinsicSize.height)
        )
    }
}

@Composable
fun FilmwebButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    val image = painterResource(Res.drawable.Filmweb)

    BaseButton(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        buttonColor = FilmWebButtonColor,
        isLoading = isLoading,
        modifier = modifier,
    ) {
        Image(
            painter = image,
            contentDescription = null,
            Modifier
                .height(32.dp)
                .width(130.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(FilmWebButtonColor.contentColor  )
        )
    }
}
@Composable
fun OnlyFilmsButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    val icon = painterResource(Res.drawable.Onlyfilms)

    BaseButton(
        onClick = onClick,
        enabled = if (isLoading) false else enabled,
        buttonColor = OnlyfilmsButtonColor,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            Modifier
                .padding(end = 3.dp)
                .fillMaxHeight(0.63f)
                .aspectRatio(icon.intrinsicSize.width / icon.intrinsicSize.height),
        )
        Text(text = "OnlyFilms",
            fontFamily = LatoFontFamily(),
            fontSize = 26.sp,
            fontWeight = FontWeight(700)
        )
    }
}