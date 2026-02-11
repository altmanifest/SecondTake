package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.PrimaryButton
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.SimpleList
import com.altmanifest.secondtake.ui.components.SimpleSearchBar
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.viewmodel.ComparisonSetupViewModel

@Composable
fun GenreScreen(
    viewModel: ComparisonSetupViewModel,
    onContinueButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.uiState
    val availableGenres = state.value.availableGenres

    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredGenres = remember(searchQuery, availableGenres) {
        if (searchQuery.isBlank()) availableGenres
        else availableGenres.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = modifier.padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(
            heading = "Genre",
            onBackClick = onBackButtonClicked,
            headingModifier = Modifier.padding(bottom = 40.dp)
        )

        SimpleList(
            list = filteredGenres,
            modifier = Modifier.weight(1f),
            selectedItem = selectedGenre,
            onItemSelected = { genre -> selectedGenre = genre }
        )

        SimpleSearchBar(
            placeholderText = "Search for genre...",
            query = searchQuery,
            onQueryChanged = { searchQuery = it },
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = SurfaceColor)
                .fillMaxWidth()
                .height(170.dp)
        ) {
            PrimaryButton(
                text = "Continue",
                enabled = selectedGenre != null,
                onClick = {
                    viewModel.selectGenre(selectedGenre!!)
                    viewModel.submitSetup()
                    onContinueButtonClicked()
                },
            )
        }
    }
}