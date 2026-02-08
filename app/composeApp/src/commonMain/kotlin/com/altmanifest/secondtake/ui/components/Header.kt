package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.SurfaceColor

@Composable
fun Header(
    heading: String? = null,
    isComparisonScreen: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
    headingModifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (onBackClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 6.dp)
            ) {
                BackNavigation(onClick = onBackClick)
            }
        }

        if (heading != null) {
            if (isComparisonScreen) {
                ComparisonHeading(
                    text = heading,
                    modifier = headingModifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = SurfaceColor)
                )
            } else {
                CustomHeading(
                    text = heading,
                    modifier = headingModifier
                        .align(Alignment.Center)
                )
            }
        }

        if (onCloseClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 6.dp)
            ) {
                HomeNavigation(onClick = onCloseClick)
            }
        }
    }
}