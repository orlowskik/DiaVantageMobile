package com.example.diavantagemobile.util.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.ui.physicians.PhysiciansViewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme

@Composable
fun FilteringBar(
    inputSort: Int,
    sortingMap: Map<Int, String>,
    onSortingChange: (Int) -> Unit,
    isExpanded: Boolean = false,
    changeExpanded: () -> Unit,
    modifier: Modifier = Modifier,
    filteringOptions: @Composable () -> Unit,
){
    Column (
        modifier = modifier

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .background(
                    color = Color.LightGray
                )
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = changeExpanded,
                ) {
                    Icon(
                        Icons.Filled.FilterList,
                        contentDescription = "Filter list",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                Text(
                    text = "Filter",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                )
            }
            SortingDropdown(
                inputSort = inputSort,
                sortingMap = sortingMap,
                onSortingChange = onSortingChange,
            )
        }
        if (isExpanded){
            Column {
                filteringOptions()
            }
        }
    }
}

@Composable
private fun SortingDropdown(
    inputSort: Int,
    sortingMap: Map<Int, String>,
    onSortingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    val isDropdownExpanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(150.dp)
            .height(35.dp)
            .border(
                width = 2.dp,
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.outline
            )
    ){
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable { isDropdownExpanded.value = true }
                .padding(5.dp)
        ) {
            Text(
                text = "Sorting: ",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            sortingMap[inputSort]?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                Icons.Filled.ExpandMore,
                contentDescription = "Expand icon",
                modifier = modifier
            )
        }
        DropdownMenu(
            expanded = isDropdownExpanded.value,
            onDismissRequest = { isDropdownExpanded.value = false}
        ) {
            sortingMap.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.value)
                    },
                    onClick = {
                        isDropdownExpanded.value = false
                        onSortingChange(it.key)
                    }
                )
            }
        }
    }
}




@Composable
@Preview(showBackground = true)
fun FilteringBarPreview(){
    DiaVantageMobileTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White
                )
        ) {
            FilteringBar(
                modifier = Modifier,
                inputSort = 0,
                onSortingChange = {},
                sortingMap = PhysiciansViewModel().sortingMap,
                isExpanded = true,
                filteringOptions = {},
                changeExpanded = { }
            )
        }
    }
}