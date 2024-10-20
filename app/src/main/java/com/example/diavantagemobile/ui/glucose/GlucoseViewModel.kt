package com.example.diavantagemobile.ui.glucose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.diavantagemobile.ui.interfaces.MeasurementViewModel
import com.example.diavantagemobile.util.api.glucose.GlucoseRepository
import com.example.diavantagemobile.util.api.responses.FailedSendGlucoseResponse
import kotlinx.coroutines.runBlocking


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
        try {
            val result = glucoseRepository.sendGlucoseMeasurement(
                patient = patient,
                measurement = inputGlucose,
                measurementType = inputType.toString(),
                measurementDate = inputDate + "T" + inputTime
            )
            glucoseResponse = result
            toggleDialog()
        } catch (e: Exception) {
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }
}