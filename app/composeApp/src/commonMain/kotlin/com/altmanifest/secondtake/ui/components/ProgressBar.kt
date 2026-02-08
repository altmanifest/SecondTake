package com.altmanifest.secondtake.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor

@Composable
fun ProgressBar(
    currentValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier,
) {
    val progressFactor = if (maxValue > 0) currentValue.toFloat() / maxValue else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progressFactor.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000),
        label = "ProgressAnimation"
    )

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(18.dp)
            .fillMaxWidth(0.62f)
            .clip(RoundedCornerShape(5.dp))
            .background(SurfaceColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .background(TextColor)
                .align(Alignment.CenterStart)
                .clipToBounds()
        )
    }
}