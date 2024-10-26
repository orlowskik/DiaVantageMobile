package com.example.diavantagemobile.util.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    options: MutableMap<String, MutableMap<String, Boolean>>,
    optionsStates: MutableMap<String, MutableSet<String>>,
    onCheckBoxChange: (String, String) -> Unit,
    onApplyPressed: (MutableMap<String, MutableSet<String>>) -> Unit = {},
    modifier: Modifier = Modifier,
){
    val isFilteringExpanded = remember { mutableStateOf(isExpanded) }


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
                    onClick = {isFilteringExpanded.value = !isFilteringExpanded.value},
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
        if (isFilteringExpanded.value){
            Column {
                FilteringOptions(
                    options = options,
                    optionsStates = optionsStates,
                    onCheckBoxChange = onCheckBoxChange,
                    onExitPressed = { isFilteringExpanded.value = !isFilteringExpanded.value},
                    onApplyPressed = onApplyPressed,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .fillMaxHeight(1f)
                )
            }

        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilteringOptions(
    options: MutableMap<String, MutableMap<String, Boolean>>,
    optionsStates: MutableMap<String, MutableSet<String>>,
    onCheckBoxChange: (String, String) -> Unit,
    onExitPressed: () -> Unit = {},
    onApplyPressed: (MutableMap<String, MutableSet<String>>) -> Unit = {},
    modifier: Modifier = Modifier
){
    var recompositionDummy by remember { mutableStateOf(true) }

    Log.i("Filtering", options.toString())
    Log.i("Filtering", optionsStates.toString())
    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        options.forEach{ entry->
            Row (
                modifier = Modifier
            ) {
                Text(
                    text = "${entry.key}:",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(
                    modifier = modifier
                )
                FlowRow (
                    modifier = Modifier
                ) {
                    entry.value.forEach{ subEntry ->
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ){
                            Text(
                                text = subEntry.key,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                            )
                                Checkbox(
                                    checked = options[entry.key]!![subEntry.key]!!,
                                    onCheckedChange = {
                                        onCheckBoxChange(entry.key, subEntry.key)
                                        recompositionDummy = !recompositionDummy
                                        if (options[entry.key]!![subEntry.key]!!){
                                            optionsStates[entry.key]?.add(subEntry.key) ?: {
                                                optionsStates[entry.key] = mutableSetOf(subEntry.key)
                                            }
                                        } else {
                                            optionsStates[entry.key]?.remove(subEntry.key)
                                        }
                                    },
                                )
                            VerticalDivider(
                                modifier = Modifier
                                    .height(10.dp)
                                    .padding(end = 5.dp)
                            )
                        }
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Button(
                onClick = onExitPressed
            ) {
                Text(
                    text = "Exit"
                )
            }
            Button(
                onClick = {
                    onApplyPressed(optionsStates)
                    },
                modifier = modifier
            ) {
                Text(
                    text = "Apply filters"
                )
            }
        }

    }
    LaunchedEffect(recompositionDummy) { }
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


val options = mutableMapOf(
    "Specialty" to mutableMapOf(
        "Cardiology" to true,
        "Neurology" to false,
        "Pediatrics" to true,
        "Orthopedics" to false,
        "Dermatology" to true,
        "Oncology" to false,
        "Psychiatry" to true,
        "Endocrinology" to false,
        "Gastroenterology" to true,
        "Ophthalmology" to false
    ),
    "Country" to mutableMapOf(
        "United States" to true,
        "Canada" to false,
        "United Kingdom" to true,
        "Germany" to false,
        "France" to true,
        "Australia" to false,
        "Japan" to true,
        "India" to false,
        "Brazil" to true,
        "South Africa" to false
    )
)

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
                options = options,
                optionsStates = mutableMapOf(),
                onCheckBoxChange = {_:String, _:String ->},
                isExpanded = true,
            )
        }
    }
}