package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import com.altmanifest.secondtake.ui.theme.TextColor

@Composable
fun SimpleList(
    list: List<String>,
    onItemSelected: (String?) -> Unit,
    selectedItem: String?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(0.78f)
    ) {
        items(list) { item ->
            val isSelected = item == selectedItem

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(62.5.dp)
                    .fillMaxWidth()
                    .background(if(isSelected) TextColor else SurfaceColor)
                    .clickable(onClick = {
                        if (isSelected) onItemSelected(null) else onItemSelected(item)
                    })
            ) {
                Text(
                    text = item,
                    color = if(isSelected) SurfaceColor else TextColor,
                    textAlign = TextAlign.Center,
                    fontSize = 17.5.sp,
                )
            }
        }
    }
}