package com.altmanifest.secondtake.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.altmanifest.secondtake.ui.components.ClickableText
import com.altmanifest.secondtake.ui.components.PrimaryButton
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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val image = painterResource(Res.drawable.Secondtake_Icon)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.13f))
            Image(
                painter = image,
                contentDescription = "Second Take Logo",
                modifier = Modifier.fillMaxWidth(0.34f)
                    .aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PrimaryButton(
                text = "Compare",
                onClick = onCompareButtonClicked,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            ClickableText(text = "Forgotten Titles" , onClick = onForgottenTitlesLinkClicked)
            Spacer( modifier = Modifier.fillMaxHeight(0.46f))
        }
    }
}