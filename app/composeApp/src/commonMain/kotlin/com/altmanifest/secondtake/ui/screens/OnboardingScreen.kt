package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.PrimaryButton
import com.altmanifest.secondtake.ui.components.CustomHeading
import com.altmanifest.secondtake.ui.components.FilmwebButton
import com.altmanifest.secondtake.ui.components.IMDBButton
import com.altmanifest.secondtake.ui.components.OnlyFilmsButton
import com.altmanifest.secondtake.ui.theme.SurfaceColor

@Composable
fun OnboardingScreen(
    onContinueButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    fun connectProvider() {
        /**
         * @TODO
         * Implement Provider connection
         * */
    }
    Column(
        modifier = modifier.padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CustomHeading(
            text = "Connect Services",
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            IMDBButton(
                enabled = true,
                onClick = { connectProvider() },
                isLoading =false,
            )
            FilmwebButton(
                enabled = true,
                onClick = { connectProvider() },
                isLoading = false,
            )
            OnlyFilmsButton(
                enabled = true,
                onClick = { connectProvider() },
                isLoading = false,
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = SurfaceColor)
                .fillMaxWidth()
                .height(170.dp)
        ) {
            PrimaryButton(
                text = "Continue",
                enabled = true, /** @TODO : Make enabled only true if at least 1 provider is connected */
                onClick = onContinueButtonClicked,
                isLoading = false,
            )
        }
    }
}