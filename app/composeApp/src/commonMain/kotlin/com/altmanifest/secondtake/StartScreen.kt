package com.altmanifest.secondtake

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StartScreen(
    onCompareButtonClicked: () -> Unit,
    onForgottenTitlesLinkClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onCompareButtonClicked) {
            Text("Compare")
        }
        Button(onClick = onForgottenTitlesLinkClicked) {
            Text("Forgotten Titles")
        }
    }
}