package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.FilmwebButton
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.IMDBButton
import com.altmanifest.secondtake.ui.components.MockifyButton
import com.altmanifest.secondtake.ui.components.OnlyFilmsButton

@Composable
fun ProviderSelectionScreen(
    onProviderButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(heading = "Choose your provider", onBackClick = onBackButtonClicked)

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopCenter) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                IMDBButton(
                    enabled = false,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
                FilmwebButton(
                    enabled = false,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
                OnlyFilmsButton(
                    enabled = true,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
                MockifyButton(
                    enabled = true,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
            }
        }
    }
}