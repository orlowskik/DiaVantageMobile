package com.example.diavantagemobile.ui.blood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.diavantagemobile.util.api.blood.BloodRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodScreen(
    bloodRepository: BloodRepository,
    modifier: Modifier = Modifier,
    bloodViewModel: BloodViewModel = viewModel(),
    returnToHome: () -> Unit = {},
    patientId: String?

){
    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Blood insert",
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
        content = { BloodContentLayout(
            inputSystolic = bloodViewModel.inputSystolic,
            inputDiastolic = bloodViewModel.inputDiastolic,
            inputPulse = bloodViewModel.inputPulse,
            inputDate = bloodViewModel.inputDate,
            inputTime = bloodViewModel.inputTime,
            onSystolicChange = fun(systolic: String){ bloodViewModel.updateSystolic(systolic) },
            onDiastolicChange = fun(diastolic: String){ bloodViewModel.updateDiastolic(diastolic) },
            onPulseChange = fun(pulse: String){ bloodViewModel.updatePulse(pulse) },
            onDateChange = fun(millis: Long?) { bloodViewModel.updateDate(millis) },
            onTimeChange = fun(time: TimePickerState?) { bloodViewModel.updateTime(time) },
            onResetButton = { bloodViewModel.resetUserInput() },
            onSendButton = {
                bloodViewModel.sendBloodMeasurement(
                    bloodRepository = bloodRepository,
                    patient = patientId
                )
                bloodViewModel.resetUserInput()
            },
            modifier = modifier,
        )},
        modifier = modifier,
    )
    CreationInfo(
        title = "Blood measurement creation status",
        bloodResponse = bloodViewModel.bloodResponse,
        showDialog = bloodViewModel.showCreationDialog,
        onDismissRequest = { bloodViewModel.toggleDialog() },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodContentLayout(
    inputSystolic: String,
    inputDiastolic: String,
    inputPulse: String,
    inputDate: String,
    inputTime: String,
    onSystolicChange: (String) -> Unit,
    onDiastolicChange: (String) -> Unit,
    onPulseChange: (String) -> Unit,
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
            value = inputSystolic,
            onValueChange = onSystolicChange,
            label = { Text("Systolic Blood Pressure Measurement Value") },
            singleLine = true,
            placeholder = { Text("mm/Hg") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(
            value = inputDiastolic,
            onValueChange = onDiastolicChange,
            label = {Text("Diastolic Blood Pressure Measurement Value")},
            singleLine = true,
            placeholder = {Text("mm/Hg")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        TextField(
            value = inputPulse,
            onValueChange = onPulseChange,
            label = {Text("Pulse Measurement Value")},
            singleLine = true,
            placeholder = {Text("beats/minute")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )



        DatePickerFieldToModal(
            inputDate = inputDate,
            onDateChange = onDateChange,
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
@Preview(showBackground = true)
fun BloodScreenPreview(){
    DiaVantageMobileTheme {
        BloodScreen(
            patientId = "11",
            bloodRepository = ID.remoteRepository.bloodRepository()
        )
    }
}