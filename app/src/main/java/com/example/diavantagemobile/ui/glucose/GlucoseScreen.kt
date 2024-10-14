package com.example.diavantagemobile.ui.glucose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.DiaVantageApi
import com.example.diavantagemobile.util.composables.DatePickerDocked
import com.example.diavantagemobile.util.composables.DatePickerFieldToModal
import com.example.diavantagemobile.util.data.TopAppBarTypes


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
            onDateChanged = fun (millis: Long?) { glucoseViewModel.updateDate(millis) },
            modifier = modifier,
        )},
        modifier = modifier,
    )
}


@Composable
fun GlucoseContentLayout(
    inputGlucose: Int,
    inputDate: String,
    inputTime: String,
    onDateChanged: (Long?) -> Unit,
    modifier: Modifier = Modifier,
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ){
        DatePickerFieldToModal(
            inputDate = inputDate,
            onDateChange = onDateChanged,
            modifier = modifier,
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