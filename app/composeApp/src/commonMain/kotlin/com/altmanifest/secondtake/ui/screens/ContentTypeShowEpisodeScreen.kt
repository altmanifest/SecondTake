package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.PrimaryButton
import com.altmanifest.secondtake.ui.viewmodel.ComparisonSetupViewModel
import com.altmanifest.secondtake.ui.viewmodel.ContentType

@Composable
fun ContentTypeShowEpisodeScreen(
    viewModel: ComparisonSetupViewModel,
    onShowButtonClicked: () -> Unit,
    onEpisodeButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(heading = "Choose your content type", onBackClick = onBackButtonClicked)

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                PrimaryButton(
                    text = "Shows",
                    onClick = {
                        viewModel.selectContentType(ContentType.SHOW)
                        onShowButtonClicked()
                    },
                    isLoading = false,
                )
                PrimaryButton(
                    text = "Episodes",
                    onClick = {
                        viewModel.selectContentType(ContentType.EPISODE)
                        onEpisodeButtonClicked()
                    },
                    isLoading = false,
                )
            }
        }
    }
}