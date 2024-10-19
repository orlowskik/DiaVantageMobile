package com.example.diavantagemobile.ui.glucose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.ui.interfaces.MeasurementViewModel
import com.example.diavantagemobile.util.data.interfaces.GlucoseRepository
import com.example.diavantagemobile.util.data.responses.FailedSendGlucoseResponse
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.EnumMap
import java.util.EnumSet
import java.util.Locale


class GlucoseViewModel : MeasurementViewModel() {

    val typesMap = mapOf(
        0 to "undefined",
        1 to "Before Breakfast",
        2 to "After Breakfast",
        3 to "Before Lunch",
        4 to "After Lunch",
        5 to "Before Dinner",
        6 to "After Dinner"
    )

    var glucoseResponse by mutableStateOf<FailedSendGlucoseResponse?>(null)
        private set

    var inputGlucose by mutableStateOf("")
        private set

    var inputType by mutableIntStateOf(0)
        private set

    fun updateGlucose(glucose: String){
        inputGlucose = glucose
    }

    fun updateType(type: Int){
        inputType = type
    }

    override fun resetUserInput(){
        super.resetUserInput()
        inputGlucose = ""
        inputType = 0
    }

    fun sendGlucoseMeasurement(glucoseRepository: GlucoseRepository, patient: String?) = runBlocking{
        val result = glucoseRepository.sendGlucoseMeasurement(
            patient = patient,
            measurement = inputGlucose,
            measurementType = inputType.toString(),
            measurementDate = inputDate + "T" + inputTime
        )
        glucoseResponse = result
        toggleDialog()
    }
}