package com.altmanifest.secondtake.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.*
import com.altmanifest.secondtake.ui.utility.darken
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.*

val DefaultButtonModifier = Modifier
    .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
    .height(80.dp)
    .fillMaxWidth(0.75f)

@Composable
fun BaseButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    buttonColor: ButtonColors = PrimaryButtonColor,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    cornerRadius: Dp = 20.dp,
    content: @Composable () -> Unit
) {
    val isButtonEnabled = enabled && !isLoading
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedAsState = interactionSource.collectIsPressedAsState()
    val isPressed = !isButtonEnabled || isPressedAsState.value

    val pressOffset by animateDpAsState(
        targetValue = if (isPressed) 6.dp else 0.dp,
        label = "buttonPressAnimation"
    )

    Button(
        onClick = onClick,
        enabled = isButtonEnabled,
        colors = buttonColor,
        shape = RoundedCornerShape(cornerRadius),
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        modifier = modifier
            .graphicsLayer {
                translationY = pressOffset.toPx()
            }
            .drawBehind {
                val currentShadowOffset = 6.dp.toPx() - pressOffset.toPx()
                drawRoundRect(
                    color = if (isButtonEnabled) {
                        buttonColor.containerColor.darken(0.6f)
                    } else {
                        buttonColor.disabledContainerColor.darken(0.6f)
                    },
                    topLeft = Offset(0f, currentShadowOffset),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
    ) {
        if (!isLoading) {
            content()
        } else {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxHeight(0.6f).aspectRatio(1f),
                color = if(isButtonEnabled) buttonColor.contentColor else buttonColor.disabledContentColor
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = DefaultButtonModifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    cornerRadius: Dp = 20.dp,
    content: @Composable () -> Unit = {}
) {
    BaseButton(
        enabled = enabled,
        onClick = onClick,
        isLoading = isLoading,
        buttonColor = PrimaryButtonColor,
        modifier = modifier,
        contentPadding = contentPadding,
        cornerRadius = cornerRadius
    ) {
        if(text != null) {
            Text(
                text = text,
                fontSize = 32.sp,
            )
        } else {
            content()
        }
    }
}

@Composable
fun IMDBButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = DefaultButtonModifier
) {
    val icon = painterResource(Res.drawable.IMDB_Logo_2016)
    BaseButton(
        onClick = onClick,
        enabled = !isLoading && enabled,
        buttonColor = IMDBButtonColor,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
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
    modifier: Modifier = DefaultButtonModifier
) {
    val image = painterResource(Res.drawable.Filmweb)
    BaseButton(
        onClick = onClick,
        enabled = !isLoading && enabled,
        buttonColor = FilmWebButtonColor,
        isLoading = isLoading,
        modifier = modifier,
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .height(32.dp)
                .width(130.dp),
            contentScale = ContentScale.FillBounds,
            colorFilter = (
                if (enabled) ColorFilter.tint(FilmWebButtonColor.contentColor)
                else ColorFilter.tint(FilmWebButtonColor.disabledContentColor)
            )
        )
    }
}

@Composable
fun OnlyFilmsButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = DefaultButtonModifier
) {
    val icon = painterResource(Res.drawable.Onlyfilms)
    BaseButton(
        onClick = onClick,
        enabled = !isLoading && enabled,
        buttonColor = OnlyfilmsButtonColor,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
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

@Composable
fun MockifyButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = DefaultButtonModifier
) {
    BaseButton(
        onClick = onClick,
        enabled = !isLoading && enabled,
        buttonColor = MockifyButtonColor,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Text(text = "Mockify",
            fontFamily = LatoFontFamily(),
            fontSize = 26.sp,
            fontWeight = FontWeight(700)
        )
    }
}