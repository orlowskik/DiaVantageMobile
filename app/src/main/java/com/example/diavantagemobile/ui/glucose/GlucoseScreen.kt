package com.example.diavantagemobile.ui.glucose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.diavantagemobile.util.api.DiaVantageApi
import com.example.diavantagemobile.util.composables.AdvancedTimePicker
import com.example.diavantagemobile.util.composables.DatePickerFieldToModal
import com.example.diavantagemobile.util.composables.TimePickerField
import com.example.diavantagemobile.util.data.TopAppBarTypes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlucoseScreen(
    modifier: Modifier = Modifier,
    glucoseViewModel: GlucoseViewModel = viewModel(),
    api: DiaVantageApi = DiaVantageApi(),
    returnToHome: () -> Unit = {}

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
            inputDate = glucoseViewModel.inputDate,
            inputTime = glucoseViewModel.inputTime,
            onGlucoseChange = fun(glucose: String) { glucoseViewModel.updateGlucose(glucose)},
            onDateChange = fun(millis: Long?) { glucoseViewModel.updateDate(millis) },
            onTimeChange = fun(time: TimePickerState?) { glucoseViewModel.updateTime(time) },
            modifier = modifier,
        )},
        modifier = modifier,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlucoseContentLayout(
    inputGlucose: String,
    inputDate: String,
    inputTime: String,
    onGlucoseChange: (String) -> Unit,
    onDateChange: (Long?) -> Unit,
    onTimeChange: (TimePickerState?) -> Unit,
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
            placeholder = { Text("80 mm/Hg") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = modifier
                .fillMaxWidth()
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

    }
}


@Composable
@Preview(showBackground = true)
fun GlucoseScreenPreview(){
    DiaVantageMobileTheme {
        GlucoseScreen(

        )
    }
}