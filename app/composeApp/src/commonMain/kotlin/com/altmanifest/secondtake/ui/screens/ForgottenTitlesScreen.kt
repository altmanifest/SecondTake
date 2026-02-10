package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.ForgottenTitleCard
import com.altmanifest.secondtake.ui.components.Header
import com.altmanifest.secondtake.ui.viewmodel.ForgottenTitlesViewModel

@Composable
fun ForgottenTitlesScreen(
    viewModel: ForgottenTitlesViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier,
) {
    LaunchedEffect(Unit){
        viewModel.startViewModel()
    }

    val state = viewModel.uiState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 40.dp),
    ) {
        Header(
            onBackClick = onBackClick,
            heading = "Forgotten titles"
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.weight(weight = 1f)
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.78f)
            ) {
                items(state.forgottenTitles) { title ->
                    ForgottenTitleCard(
                        title = title.value,
                        imageURI = title.posterUrl,
                        onDeleteClick = { viewModel.onDelete(title) },
                        onWatchlistClick = { viewModel.onRestore(title) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}