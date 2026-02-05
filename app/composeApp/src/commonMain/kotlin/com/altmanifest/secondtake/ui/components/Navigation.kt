package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.IconButtonColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.chevron_left
import secondtake.composeapp.generated.resources.x_mark

@Composable
fun BackNavigation(onClick: () -> Unit) {
    IconButton(onClick = onClick, colors = IconButtonColor) {
        Icon(
            painterResource(Res.drawable.chevron_left),
            contentDescription = "Go Back",
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun HomeNavigation(onClick: () -> Unit) {
    IconButton(onClick = onClick, colors = IconButtonColor) {
        Icon(
            painterResource(Res.drawable.x_mark),
            contentDescription = "Back to Start",
            modifier = Modifier.size(48.dp)
        )
    }
}