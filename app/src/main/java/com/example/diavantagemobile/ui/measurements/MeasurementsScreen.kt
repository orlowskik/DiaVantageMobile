package com.example.diavantagemobile.ui.measurements

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.blood.BloodRepository
import com.example.diavantagemobile.util.api.glucose.GlucoseRepository
import com.example.diavantagemobile.util.api.responses.BloodResponse
import com.example.diavantagemobile.util.api.responses.GlucoseResponse
import com.example.diavantagemobile.util.composables.FilteringBar
import com.example.diavantagemobile.util.data.TopAppBarTypes

@Composable
fun MeasurementsScreen(
    measurementsViewModel: MeasurementsViewModel = viewModel(),
    glucoseRepository: GlucoseRepository,
    bloodRepository: BloodRepository,
    modifier: Modifier = Modifier,
    returnHome: () -> Unit,
){
    val glucoseMeasurements by measurementsViewModel.glucoseMeasurements.collectAsState()
    val bloodMeasurements by measurementsViewModel.bloodMeasurements.collectAsState()
    val filteringMap by measurementsViewModel.filteringMap.collectAsState()
    val filteringMapScheme by measurementsViewModel.filteringMapScheme.collectAsState()
    val measurementShowMap by measurementsViewModel.measurementsShowMap.collectAsState()

    LaunchedEffect(true) {
        measurementsViewModel.reloadMeasurements(
            glucoseRepository = glucoseRepository,
            bloodRepository = bloodRepository
        )
        measurementsViewModel.sortMeasurements()
    }

    Log.i("measurements", measurementShowMap.toString())

    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Measurements",
                appBarType = TopAppBarTypes.SmallTopAppBar,
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {returnHome()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = modifier
            )
        },
        content = {
            MeasurementsContentLayout(
                glucoseList = glucoseMeasurements,
                bloodList = bloodMeasurements,
                measurementShowMap = measurementShowMap,
                modifier = modifier,
                inputSort = measurementsViewModel.inputSorting,
                sortingMap = measurementsViewModel.sortingMap,
                options = filteringMapScheme,
                optionsStates = filteringMap,
                onSortingChange = fun(sorting: Int){
                    measurementsViewModel.updateSorting(sorting)
                    measurementsViewModel.sortMeasurements()
                },
                onCheckBoxChange = measurementsViewModel::toggleMapIndex,
                onApplyPressed = measurementsViewModel::applyFiltering,
            )
        }
    )
}



@Composable
fun MeasurementsContentLayout(
    glucoseList: List<GlucoseResponse>,
    bloodList: List<BloodResponse>,
    measurementShowMap: Map<String, Boolean>,
    inputSort: Int,
    isSearching: Boolean = false,
    sortingMap: Map<Int, String>,
    options: MutableMap<String, MutableMap<String, Boolean>> = mutableMapOf(),
    optionsStates: MutableMap<String, MutableSet<String>> = mutableMapOf(),
    onSortingChange: (Int) -> Unit,
    onApplyPressed: () -> Unit,
    onCheckBoxChange: (String, String) -> Unit = { _: String, _: String -> },
    modifier: Modifier = Modifier
){
    val isFilteringExpanded = remember { mutableStateOf(false) }

    Column (
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .padding(15.dp)
    ) {
        FilteringBar(
            inputSort = inputSort,
            sortingMap = sortingMap,
            onSortingChange = onSortingChange,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            isExpanded = isFilteringExpanded.value,
            changeExpanded = {isFilteringExpanded.value = !isFilteringExpanded.value},
        ){
            MeasurementFilteringOptions(
                modifier = modifier,
                options = options ,
                optionsStates = optionsStates,
                onCheckBoxChange = onCheckBoxChange,
                onExitPressed = { isFilteringExpanded.value = false },
                onApplyPressed = onApplyPressed
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            if(isSearching){
                Box(
                    modifier = modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
            } else{
                if (measurementShowMap["Glucose"] == true) {
                    Card(
                        modifier = modifier
                            .padding(vertical = 10.dp),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Glucose Measurements"
                            )
                        }
                    }
                    if(glucoseList.isEmpty()){
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(top = 25.dp)
                        ) {
                            Text(
                                text = "No glucose measurements found",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        glucoseList.forEach {
                            MeasurementCard(
                                glucose = it,
                                modifier = modifier
                                    .padding(bottom = 10.dp)
                            )
                        }
                    }
                }

                if (measurementShowMap["Blood"] == true) {
                    Card(
                        modifier = modifier
                            .padding(vertical = 10.dp),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary
                        )

                    ) {
                        Row (
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Blood Pressure Measurements"
                            )
                        }
                    }
                    if (bloodList.isEmpty()){
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(top = 25.dp)
                        ) {
                            Text(
                                text = "No blood pressure measurements found",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        bloodList.forEach() {
                            MeasurementCard(
                                blood = it,
                                modifier = modifier
                                    .padding(bottom = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MeasurementCard(
    glucose: GlucoseResponse? = null,
    blood: BloodResponse? = null,
    modifier: Modifier = Modifier
){
    if (glucose == null && blood == null) return

    Card(
        modifier = modifier
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(16.dp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Min)
                .padding(5.dp)
        ){
            if (glucose != null){
                Text(
                    text = "Glucose Measurement:",
                    style = MaterialTheme.typography.labelSmall
                )
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ){
                    Text(
                        text = glucose.measurement.toString(),
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(10.dp)
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = glucose.getMeasurementType()
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(10.dp)
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = glucose.getMeasurementDate(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else if (blood != null){
                Text(
                    text = "Blood Pressure Measurement:",
                    style = MaterialTheme.typography.labelSmall
                )
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ){
                    Text(
                        text = "${blood.systolic_pressure}/${blood.diastolic_pressure}"
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(10.dp)
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = blood.pulse_rate.toString()
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(10.dp)
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = blood.getMeasurementDate(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }


}




@Composable
@Preview(showBackground = true)
fun MeasurementCardPreview(){
    DiaVantageMobileTheme {
        Column (
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(10.dp)
        ){
            MeasurementCard()
            MeasurementCard(
                glucose = GlucoseResponse(
                    measurement = 120.0,
                    measurement_type = 1,
                    measurement_date = "2024-05-05T12:30"
                )
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            MeasurementCard(
                blood = BloodResponse(
                    systolic_pressure = 120,
                    diastolic_pressure = 70,
                    pulse_rate = 76,
                    measurement_date = "2024-05-05T12:30"
                )
            )
        }
    }
}


val glucoseList = listOf(
    GlucoseResponse(
        measurement = 140.0,
        measurement_type = 3,
        measurement_date = "2024-05-06T12:30"
    ),
    GlucoseResponse(
        measurement = 120.0,
        measurement_type = 0,
        measurement_date = "2024-05-05T12:30"
    ),
    GlucoseResponse(
        measurement = 78.0,
        measurement_type = 1,
        measurement_date = "2024-05-04T12:30"
    ),
    GlucoseResponse(
        measurement = 130.0,
        measurement_type = 5,
        measurement_date = "2024-05-03T12:30"
    ),
    GlucoseResponse(
        measurement = 99.0,
        measurement_type = 3,
        measurement_date = "2024-05-02T12:30"
    ),
    GlucoseResponse(
        measurement = 240.0,
        measurement_type = 5,
        measurement_date = "2024-05-01T12:30"
    )
)


val bloodList = listOf(
    BloodResponse(
        systolic_pressure = 120,
        diastolic_pressure = 70,
        pulse_rate = 76,
        measurement_date = "2024-05-05T12:30"
    ),
    BloodResponse(
        systolic_pressure = 114,
        diastolic_pressure = 65,
        pulse_rate = 88,
        measurement_date = "2024-05-04T12:30"
    ),
    BloodResponse(
        systolic_pressure = 111,
        diastolic_pressure = 88,
        pulse_rate = 94,
        measurement_date = "2024-05-03T12:30"
    ),
    BloodResponse(
        systolic_pressure = 100,
        diastolic_pressure = 56,
        pulse_rate = 65,
        measurement_date = "2024-05-02T12:30"
    ),
    BloodResponse(
        systolic_pressure = 156,
        diastolic_pressure = 98,
        pulse_rate = 56,
        measurement_date = "2024-05-01T12:30"
    )
)


@Composable
@Preview(showBackground = true)
fun MeasurementScreenPreview(){
    DiaVantageMobileTheme {

        ScreenScaffoldTemplate(
            topBar = {
                CreateTopAppBar(
                    title = "Measurements",
                    appBarType = TopAppBarTypes.SmallTopAppBar,
                    actions = {},
                    navigationIcon = {
                        IconButton (onClick = { } ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    modifier = Modifier
                )
            },
            content = {
                MeasurementsContentLayout(
                    glucoseList = glucoseList,
                    bloodList = bloodList,
                    isSearching = false,
                    modifier = Modifier,
                    inputSort = TODO(),
                    sortingMap = TODO(),
                    onSortingChange = TODO(),
                    onApplyPressed = TODO(),
                    measurementShowMap = TODO(),
                    options = TODO(),
                    optionsStates = TODO(),
                    onCheckBoxChange = TODO()
                )
            }
        )
    }
}