package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.FilmwebButton
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.IMDBButton
import com.altmanifest.secondtake.ui.components.OnlyFilmsButton

@Composable
fun ProviderSelectionScreen(
    onProviderButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    fun fetchConnectedProviders() {
        /**
         * @TODO
         * Implement connected Providers fetch
         * */
    }

    Column(
        modifier = modifier.padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(heading = "Choose your provider", onBackClick = onBackButtonClicked)

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                IMDBButton(
                    enabled = true,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
                FilmwebButton(
                    enabled = true,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
                OnlyFilmsButton(
                    enabled = true,
                    onClick = { onProviderButtonClicked() },
                    isLoading = false,
                )
            }
        }
    }
}