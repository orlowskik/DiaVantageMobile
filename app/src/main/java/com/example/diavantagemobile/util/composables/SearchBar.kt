package com.example.diavantagemobile.util.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme


@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(50.dp)
            )
            .width(IntrinsicSize.Min)
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { onSearchTextChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search icon"
                    )
                }
            },
            textStyle = MaterialTheme.typography.bodySmall,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = RoundedCornerShape(60.dp),
            modifier = modifier
                .width(200.dp)
                .height(50.dp)

        )
    }
}


@Composable
@Preview(showBackground = true)
fun SearchBarEmptyPreview(){
    DiaVantageMobileTheme {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            SearchBar(
                searchText = "",
                onSearchTextChange = {},
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SearchBarInputPreview(){
    DiaVantageMobileTheme {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            SearchBar(
                searchText = "Some input that will hide when overlap",
                onSearchTextChange = {},
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

