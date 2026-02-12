package com.altmanifest.secondtake.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.domain.Choice
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Preference
import com.altmanifest.secondtake.ui.components.ContentCard
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.components.MenuDirection
import com.altmanifest.secondtake.ui.components.MovieRatingDialog
import com.altmanifest.secondtake.ui.components.ProgressBar
import com.altmanifest.secondtake.ui.components.RadialMenu
import com.altmanifest.secondtake.ui.components.SessionFinishedDialog
import com.altmanifest.secondtake.ui.viewmodel.ComparisonViewModel
import kotlinx.coroutines.launch

@Composable
fun ComparisonScreen(
    viewModel: ComparisonViewModel,
    onHomeButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.startViewModel()
    }

    val scope = rememberCoroutineScope()

    val state = viewModel.uiState

    val comparison = state.currentComparison
    val headerProgressBarWidth = 0.62f

    var selectedPreference by remember { mutableStateOf<Preference?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dialogTitle = remember(selectedPreference, comparison) {
        when (selectedPreference) {
            Preference.FIRST -> comparison?.first?.value
            Preference.SECOND -> comparison?.second?.value
            null -> ""
        } ?: ""
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = modifier.padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                heading = comparison?.genre?.value ?: "Comparison",
                onBackClick = onBackButtonClicked,
                onCloseClick = onHomeButtonClicked,
                isComparisonScreen = true,
                headingModifier = Modifier.fillMaxWidth(headerProgressBarWidth)
            )
            ProgressBar(
                currentValue = state.currentStep,
                maxValue = state.totalSteps,
                modifier = Modifier
                    .padding(bottom = 24.dp, top = 12.dp)
                    .fillMaxWidth(headerProgressBarWidth)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
            ){
                // left card
                ContentCard(
                    imageURI = comparison?.first?.posterUrl ?: "",
                    title = comparison?.first?.value ?: "First",
                    rating = comparison?.first?.rating?.value ?: 0.0,
                    modifier = Modifier.weight(1f),
                    onLongClick = {
                        selectedPreference = Preference.FIRST
                        showDialog = true
                    },
                    onClick = { scope.launch {
                        viewModel.onRate(
                            preference = Preference.FIRST,
                            ratingStrength = Comparison.Rating.Strength.LOW
                        )
                    } }
                )
                // right card
                ContentCard(
                    imageURI = comparison?.second?.posterUrl ?: "",
                    title = comparison?.second?.value ?: "Second",
                    rating = comparison?.second?.rating?.value ?: 0.0,
                    modifier = Modifier.weight(1f),
                    onLongClick = {
                        selectedPreference = Preference.SECOND
                        showDialog = true
                    },
                    onClick = { scope.launch {
                        viewModel.onRate(
                        preference = Preference.SECOND,
                        ratingStrength = Comparison.Rating.Strength.LOW
                        )
                    } }
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                RadialMenu(
                    onActionTriggered = { direction ->
                        scope.launch {
                            when(direction) {
                                MenuDirection.TOP -> viewModel.onForget(Choice.BOTH)
                                MenuDirection.LEFT -> viewModel.onForget(Choice.FIRST)
                                MenuDirection.RIGHT -> viewModel.onForget(Choice.SECOND)
                                MenuDirection.BOTTOM -> viewModel.onSkip()
                            }

                        }
                    }
                )
            }
        }
        // Popup Dialog
        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn() + scaleIn(initialScale = 0.9f),
            exit = fadeOut() + scaleOut(targetScale = 0.9f)
        ) {
            MovieRatingDialog(
                movieTitle = dialogTitle,
                onDismiss = { showDialog = false },
                onBetterClick = {
                    selectedPreference?.let { pref ->
                        scope.launch {
                            viewModel.onRate(
                                preference = pref,
                                ratingStrength = Comparison.Rating.Strength.MEDIUM
                            ) }
                        }
                    showDialog = false
                },
                onBestClick = {
                    selectedPreference?.let { pref ->
                        scope.launch {
                            viewModel.onRate(
                                preference = pref,
                                ratingStrength = Comparison.Rating.Strength.HIGH)
                        }
                    }
                    showDialog = false
                }
            )
        }
        if (state.isFinished) {
            SessionFinishedDialog(
                onTakeMeBack = onHomeButtonClicked
            )
        }
    }
}