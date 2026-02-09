package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.altmanifest.secondtake.ui.theme.StarRatingColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Image_Placeholder
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.Star

@Composable
fun ContentCard(
    imageURI: String,
    title: String,
    rating: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    ) {
    val imagePlaceholder = painterResource(Res.drawable.Image_Placeholder)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = SurfaceColor)
            .padding(horizontal = 4.dp, vertical = 12.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
    ) {
        AsyncImage(
            model = imageURI.ifBlank { null },
            contentDescription = "$title Poster",
            placeholder = imagePlaceholder,
            error = imagePlaceholder,
            fallback = imagePlaceholder,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(10.dp))
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = rating.toString(),
                fontSize = 20.sp,
                color = TextColor
                )
            Icon(
                painter = painterResource(Res.drawable.Star),
                contentDescription = "Star rating",
                tint = StarRatingColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = title,
            fontSize = 20.sp,
            color = TextColor,
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}