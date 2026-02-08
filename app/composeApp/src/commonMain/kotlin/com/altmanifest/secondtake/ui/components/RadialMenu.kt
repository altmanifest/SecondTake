package com.altmanifest.secondtake.ui.components

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.SurfaceBorderColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Single_Questionmark
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.Upper_Questionmarks
import secondtake.composeapp.generated.resources.fast_forward_fill
import secondtake.composeapp.generated.resources.grip_horizontal
import kotlin.math.roundToInt

enum class MenuDirection {
    TOP, RIGHT, BOTTOM, LEFT
}

@Composable
fun RadialMenu(
    modifier: Modifier = Modifier,
    collapsedSize: Dp = 60.dp,
    expandedSize: Dp = 200.dp,
    centerIcon: Painter = painterResource(Res.drawable.grip_horizontal),
    onActionTriggered: (MenuDirection) -> Unit
) {
    // Current offset of the draggable thumb relative to the center
    var thumbOffset by remember { mutableStateOf(Offset.Zero) }

    var isExpanded by remember { mutableStateOf(false) }

    // Constants for calculations
    val density = LocalDensity.current
    val maxRadiusPx = with(density) { (expandedSize / 2).toPx() - (collapsedSize / 2).toPx() }
    val expandedRadiusPx = with(density) { (expandedSize / 2).toPx() }

    // Threshold to detect if dropped over an icon (e.g., 30dp radius around the target)
    val hitThresholdPx = with(density) { 30.dp.toPx() }

    // Animation for returning to center
    val animatedOffset by animateOffsetAsState(targetValue = thumbOffset, label = "thumbAnimation")

    Box(
        modifier = modifier.size(expandedSize),
        contentAlignment = Alignment.Center
    ) {
        // Large Circle
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .size(expandedSize)
                    .clip(CircleShape)
                    .background(Color(0xFF141414))
                    .border(2.dp, SurfaceBorderColor, CircleShape)
            ) {
                // Top
                ActionIcon(
                    icon = painterResource(Res.drawable.Upper_Questionmarks),
                    modifier = Modifier.align(Alignment.TopCenter).offset(y = 8.dp).size(64.dp)
                )
                // Left
                ActionIcon(
                    icon = painterResource(Res.drawable.Single_Questionmark),
                    modifier = Modifier.align(Alignment.CenterStart).offset(x = 16.dp)
                )
                // Right
                ActionIcon(
                    icon = painterResource(Res.drawable.Single_Questionmark),
                    modifier = Modifier.align(Alignment.CenterEnd).offset(x = (-16).dp)
                )
                // Bottom
                ActionIcon(
                    icon = painterResource(Res.drawable.fast_forward_fill),
                    modifier = Modifier.align(Alignment.BottomCenter).offset(y = (-16).dp).size(32.dp)
                )
            }
        }

        // Small Circle
        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffset.x.roundToInt(), animatedOffset.y.roundToInt()) }
                .size(collapsedSize)
                .clip(CircleShape)
                .background(SurfaceColor)
                .border(2.dp, SurfaceBorderColor, CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isExpanded = true
                        },
                        onDragEnd = {
                            // Check if dropped over a target
                            val triggeredDirection = checkHit(thumbOffset, expandedRadiusPx, hitThresholdPx)

                            if (triggeredDirection != null) {
                                onActionTriggered(triggeredDirection)
                            }

                            // Reset state
                            isExpanded = false
                            thumbOffset = Offset.Zero
                        },
                        onDragCancel = {
                            isExpanded = false
                            thumbOffset = Offset.Zero
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            // Calculate new position
                            val newPos = thumbOffset + dragAmount

                            // Keep inside circle
                            thumbOffset = limitOffsetInsideCircle(newPos, maxRadiusPx)
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = centerIcon,
                contentDescription = "Draggable Icon",
                tint = Color(0xFF858585),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun ActionIcon(
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = icon,
        contentDescription = null,
        tint = Color(0xFF858585),
        modifier = modifier.size(48.dp)
    )
}

/**
 * Helper function to keep the drag offset strictly within a circular radius.
 */
fun limitOffsetInsideCircle(currentOffset: Offset, maxRadius: Float): Offset {
    val distance = currentOffset.getDistance()
    return if (distance <= maxRadius) {
        currentOffset
    } else {
        // Normalize vector and scale to maxRadius
        val ratio = maxRadius / distance
        currentOffset * ratio
    }
}

/**
 * Calculates if the drop position is close enough to one of the cardinal directions.
 */
fun checkHit(offset: Offset, containerRadius: Float, threshold: Float): MenuDirection? {
    // Positions of icons relative to center (0,0)
    // Note: Adjust padding (e.g. 28dp) to match UI layout exactly
    val iconDistance = containerRadius - 40f // Approximation based on padding

    val topPos = Offset(0f, -iconDistance)
    val bottomPos = Offset(0f, iconDistance)
    val leftPos = Offset(-iconDistance, 0f)
    val rightPos = Offset(iconDistance, 0f)

    return when {
        (offset - topPos).getDistance() < threshold -> MenuDirection.TOP
        (offset - bottomPos).getDistance() < threshold -> MenuDirection.BOTTOM
        (offset - leftPos).getDistance() < threshold -> MenuDirection.LEFT
        (offset - rightPos).getDistance() < threshold -> MenuDirection.RIGHT
        else -> null
    }
}