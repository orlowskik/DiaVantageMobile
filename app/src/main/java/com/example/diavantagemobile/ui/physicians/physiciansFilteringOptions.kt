package com.example.diavantagemobile.ui.physicians

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.composables.FilteringBar


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhysiciansFilteringOptions(
    options: MutableMap<String, MutableMap<String, Boolean>>,
    optionsStates: MutableMap<String, MutableSet<String>>,
    countryCityMapping: Map<String, String>? = null,
    onCheckBoxChange: (String, String) -> Unit,
    onExitPressed: () -> Unit = {},
    onApplyPressed: () -> Unit = {},
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
            if (entry.key == "City" && optionsStates["Country"]?.isEmpty() == true) return@forEach
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
                    entry.value.forEach inner@{ subEntry ->
                        if (entry.key == "City" && optionsStates["Country"]?.contains(
                                countryCityMapping?.get(subEntry.key)
                            ) != true) return@inner
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
                    onApplyPressed()
                    onExitPressed()
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
fun PhysiciansFilteringBarPreview(){
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
                changeExpanded = { }
            ){
                PhysiciansFilteringOptions(
                    options = options,
                    optionsStates = mutableMapOf(),
                    countryCityMapping = mapOf(),
                    onCheckBoxChange = {_: String, _: String -> },
                    onApplyPressed = {},
                    onExitPressed = {},
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .fillMaxHeight(1f)
                )
            }
        }
    }
}