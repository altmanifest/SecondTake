package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.PrimaryButtonColor
import com.altmanifest.secondtake.ui.theme.SurfaceBorderColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor

@Composable
fun SessionFinishedDialog(
    onTakeMeBack: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x80000000))
            .clickable(enabled = false) {}
    ) {
        Column(
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
                .padding(vertical = 33.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "No more comparisons left",
                color = TextColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            PrimaryButton(
                onClick = onTakeMeBack,
                isLoading = false,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                cornerRadius = 12.dp
            ) {
                Text(
                    text = "Take me back",
                    color = PrimaryButtonColor.contentColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}