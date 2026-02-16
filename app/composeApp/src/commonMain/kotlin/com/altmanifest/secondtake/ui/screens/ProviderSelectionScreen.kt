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
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.ProviderButton
import com.altmanifest.secondtake.ui.viewmodel.ProviderSelectionViewModel

@Composable
fun ProviderSelectionScreen(
    onProviderButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProviderSelectionViewModel,
) {
    val state = viewModel.uiState

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
                state.connectedProviders.forEach {
                    ProviderButton(
                        provider = it.id,
                        enabled = it.isActive,
                        onClick = { onProviderButtonClicked() }
                    )
                }
            }
        }
    }
}