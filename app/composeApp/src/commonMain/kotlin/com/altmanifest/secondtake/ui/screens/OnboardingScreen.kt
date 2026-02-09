package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.PrimaryButton
import com.altmanifest.secondtake.ui.components.FilmwebButton
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.IMDBButton
import com.altmanifest.secondtake.ui.components.MockifyButton
import com.altmanifest.secondtake.ui.components.OnlyFilmsButton
import com.altmanifest.secondtake.ui.theme.SurfaceColor

@Composable
fun OnboardingScreen(
    onContinueButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(heading = "Connect services")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 44.dp)
        ) {
            IMDBButton(
                enabled = false,
                onClick = { },
                isLoading =false,
            )
            FilmwebButton(
                enabled = false,
                onClick = {  },
                isLoading = false,
            )
            OnlyFilmsButton(
                enabled = true,
                onClick = {  },
                isLoading = false,
            )
            MockifyButton(
                enabled = true,
                onClick = {  },
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
                enabled = true,
                onClick = onContinueButtonClicked,
                isLoading = false,
            )
        }
    }
}