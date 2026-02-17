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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.ui.components.PrimaryButton
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.ProviderButton
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.viewmodel.OnboardingViewmodel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onContinueButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: OnboardingViewmodel
) {
    val scope = rememberCoroutineScope()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(heading = "Connect services")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            state.providers.forEach { provider ->
                when (provider) {
                    is AvailableProvider.Connected -> ProviderButton(
                        provider = provider.id,
                        enabled = provider.isActive
                    )

                    is AvailableProvider.Planned -> ProviderButton(
                        provider = provider.id,
                        enabled = provider.isActive
                    )

                    is AvailableProvider.Disconnected -> ProviderButton(
                        provider = provider.id,
                        enabled = provider.isActive,
                        onClick = {
                            scope.launch {
                                viewmodel.connectProvider(provider)
                            }
                        }
                    )
                }
            }
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
                enabled = state.isOneProviderConnected,
                onClick = onContinueButtonClicked,
                isLoading = false,
            )
        }
    }
}