package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.components.ClickableText
import com.altmanifest.secondtake.ui.components.CustomButton
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.Secondtake_Icon

@Composable
fun StartScreen(
    onCompareButtonClicked: () -> Unit,
    onForgottenTitlesLinkClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 110.dp, bottom = 225.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(Res.drawable.Secondtake_Icon),
            contentDescription = "Second Take Logo",
            modifier = Modifier.width(134.dp).height(139.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomButton( text = "Compare", onClick = onCompareButtonClicked)
            ClickableText(text = "Forgotten Titles" , onClick = onForgottenTitlesLinkClicked)
        }
    }
}