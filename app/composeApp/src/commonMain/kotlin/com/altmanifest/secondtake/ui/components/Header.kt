package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    heading: String? = null,
    onBackClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (onBackClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 12.dp)
            ) {
                BackNavigation(onClick = onBackClick)
            }
        }

        if (heading != null) {
            CustomHeading(
                text = heading,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (onCloseClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 12.dp)
            ) {
                HomeNavigation(onClick = onCloseClick)
            }
        }
    }
}