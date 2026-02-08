package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.PrimaryButtonColor
import com.altmanifest.secondtake.ui.theme.SurfaceBorderColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.chevron_double_up
import secondtake.composeapp.generated.resources.chevron_triple_up

@Composable
fun MovieRatingDialog(
    movieTitle: String?,
    onBetterClick: () -> Unit,
    onBestClick: () -> Unit,
    onDismiss: () -> Unit,
    ) {
    if (movieTitle == null) return

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x80000000))
            .clickable(
                // Clicking background closes dialog
                interactionSource = remember { MutableInteractionSource() },
                indication = null // No ripple effect
            ) { onDismiss() }
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(color = SurfaceColor, shape = RoundedCornerShape(size = 10.dp))
                .border(
                    width = 2.dp,
                    color = SurfaceBorderColor,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(vertical = 33.dp)
        ) {
            Text(
                text = "How much better is $movieTitle?",
                color = TextColor,
                fontSize = 20.sp,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PrimaryButton(
                    onClick = onBetterClick,
                    isLoading = false,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(60.dp),
                    cornerRadius = 12.dp
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.chevron_double_up),
                        contentDescription = "Better",
                        tint = PrimaryButtonColor.contentColor,
                        modifier = Modifier.size(55.dp)
                    )
                }
                PrimaryButton(
                    onClick = onBestClick,
                    isLoading = false,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(60.dp),
                    cornerRadius = 12.dp
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.chevron_triple_up),
                        contentDescription = "Best",
                        tint = PrimaryButtonColor.contentColor,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}