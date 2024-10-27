package com.example.diavantagemobile.ui.glucose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.ID
import com.example.diavantagemobile.util.composables.CreationInfo
import com.example.diavantagemobile.util.composables.DatePickerFieldToModal
import com.example.diavantagemobile.util.composables.TimePickerField
import com.example.diavantagemobile.util.data.TopAppBarTypes
import com.example.diavantagemobile.util.api.glucose.GlucoseRepository
import com.example.diavantagemobile.util.composables.ErrorDialogField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlucoseScreen(
    glucoseRepository: GlucoseRepository,
    modifier: Modifier = Modifier,
    glucoseViewModel: GlucoseViewModel = viewModel(),
    returnToHome: () -> Unit = {},
    patientId: String?,


    ){
    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Glucose insert",
                appBarType = TopAppBarTypes.CenterAlignedTopAppBar,
                actions = {},
                navigationIcon = {
                    IconButton (onClick = { returnToHome() } ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = modifier
            )
        },
        content = { GlucoseContentLayout(
            inputGlucose = glucoseViewModel.inputGlucose,
            inputType =  glucoseViewModel.inputType,
            inputDate = glucoseViewModel.inputDate,
            inputTime = glucoseViewModel.inputTime,
            measurementTypes = glucoseViewModel.typesMap,
            onGlucoseChange = fun(glucose: String) { glucoseViewModel.updateGlucose(glucose)},
            onTypeChange = fun(type: Int){ glucoseViewModel.updateType(type)},
            onDateChange = fun(millis: Long?) { glucoseViewModel.updateDate(millis) },
            onTimeChange = fun(time: TimePickerState?) { glucoseViewModel.updateTime(time) },
            onResetButton = {
                            glucoseViewModel.resetUserInput()

                            },
            onSendButton = {
                            glucoseViewModel.sendGlucoseMeasurement(
                            glucoseRepository = glucoseRepository,
                            patient = patientId)

                            glucoseViewModel.updateGlucose("")
                           },
            modifier = modifier,
        )},
        modifier = modifier,
    )
    CreationInfo(
        title = "Glucose measurement creation status",
        glucoseResponse = glucoseViewModel.glucoseResponse,
        showDialog = glucoseViewModel.showCreationDialog,
        onDismissRequest = { glucoseViewModel.toggleDialog() },
        modifier = modifier
    )

    ErrorDialogField(
        title = "Glucose measurement Error",
        apiViewModel = glucoseViewModel,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlucoseContentLayout(
    inputGlucose: String,
    inputType: Int,
    inputDate: String,
    inputTime: String,
    measurementTypes: Map<Int, String>,
    onGlucoseChange: (String) -> Unit,
    onTypeChange: (Int) -> Unit,
    onDateChange: (Long?) -> Unit,
    onTimeChange: (TimePickerState?) -> Unit,
    onResetButton: () -> Unit,
    onSendButton: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ){

        TextField(
            value = inputGlucose,
            onValueChange = onGlucoseChange,
            label = { Text("Glucose Measurement Value") },
            singleLine = true,
            placeholder = { Text("mmHg") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = modifier
                .fillMaxWidth()
        )

        MeasurementTypeInput(
            inputType = inputType,
            typesMap = measurementTypes,
            onTypeChange = onTypeChange,
            modifier = modifier
                .padding(top = 10.dp)
        )

        DatePickerFieldToModal(
            inputDate = inputDate,
            onDateChange = onDateChange,
            label = "Measurement Date",
            modifier = modifier,
        )

        TimePickerField(
            inputTime = inputTime,
            onTimeChange = onTimeChange,
            modifier = modifier
        )

        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ){
            OutlinedButton(
                onClick = onResetButton
            ) {
                Text(
                    "Reset",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            ElevatedButton(
                onClick = onSendButton
            ) {
                Text(
                    "Send",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
fun MeasurementTypeInput(
    inputType: Int,
    typesMap: Map<Int, String>,
    onTypeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val isDropdownExpanded = remember { mutableStateOf(false) }



    Box (
        modifier = modifier
            .height(50.dp)
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.outline
            )
    ){
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable { isDropdownExpanded.value = true }
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 15.dp)
        ) {
            Text(
                text = "Measurement type: ",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            typesMap[inputType]?.let {
                Text(
                    text = it
                )
            }

            Icon(
                Icons.Filled.ExpandMore,
                contentDescription = "Expand icon",
                modifier = modifier
                    .padding(bottom = 10.dp)
            )
        }
        DropdownMenu(
            expanded = isDropdownExpanded.value,
            onDismissRequest = { isDropdownExpanded.value = false}
        ) {
            typesMap.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.value)
                    },
                    onClick = {
                        isDropdownExpanded.value = false
                        onTypeChange(it.key)
                    }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun GlucoseScreenPreview(){
    DiaVantageMobileTheme {
        GlucoseScreen(
            glucoseRepository = ID.remoteRepository.glucoseRepository(),
            patientId = "11"
        )
    }
}