package com.altmanifest.secondtake.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.altmanifest.secondtake.ui.theme.SurfaceBorderColor
import com.altmanifest.secondtake.ui.theme.SurfaceColor
import org.jetbrains.compose.resources.painterResource
import secondtake.composeapp.generated.resources.Res
import secondtake.composeapp.generated.resources.SearchIcon
import secondtake.composeapp.generated.resources.x_mark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    placeholderText: String = "Search...",
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    val containerColor = if (isFocused) SurfaceBorderColor else SurfaceColor
    val contentColor = if (isFocused) Color(0xFFCCCCCC) else Color(0xFF9E9E9E)

    val colors = SearchBarDefaults.colors(
        containerColor = containerColor,
        dividerColor = SurfaceBorderColor,
    )

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChanged,
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = { Text(placeholderText) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.SearchIcon),
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { onQueryChanged("") }) {
                                Icon(
                                    painter = painterResource(Res.drawable.x_mark),
                                    contentDescription = "Clear Search",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .onFocusChanged({ isFocused = it.isFocused })
                        .border(width = 1.dp, color = SurfaceBorderColor, shape = RoundedCornerShape(10.dp))
                        .fillMaxSize()
                )
            },
            expanded = false,
            onExpandedChange = { },
            modifier = modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = colors,
            content = {}
        )
    }
}