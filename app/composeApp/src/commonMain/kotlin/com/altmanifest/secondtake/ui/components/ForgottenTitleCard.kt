package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.altmanifest.secondtake.ui.theme.DangerColor
import com.altmanifest.secondtake.ui.theme.LatoFontFamily
import com.altmanifest.secondtake.ui.theme.PrimaryColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Image_Placeholder
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.delete
import secondtake.composeapp.generated.resources.reply_fill

@Composable
fun ForgottenTitleCard (
    title: String,
    imageURI: String,
    onDeleteClick: () -> Unit,
    onWatchlistClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val imagePlaceholder = painterResource(Res.drawable.Image_Placeholder)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.5.dp),
        modifier = modifier
            .height(75.dp)
            .background(color = SurfaceColor)
            .padding(end = 12.5.dp)
    ) {
        AsyncImage(
            model = imageURI.ifBlank { null },
            contentDescription = "$title Poster",
            placeholder = imagePlaceholder,
            error = imagePlaceholder,
            fallback = imagePlaceholder,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(2f / 3f)
        )
        Text(
            text = title,
            fontFamily = LatoFontFamily(),
            color = TextColor,
            fontSize = 17.5.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
            ){
            IconButton(onClick = onWatchlistClick) {
                Icon(
                    painterResource(Res.drawable.reply_fill),
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(31.dp)
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painterResource(Res.drawable.delete),
                    contentDescription = null,
                    tint = DangerColor,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}