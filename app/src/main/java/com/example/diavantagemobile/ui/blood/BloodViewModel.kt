package com.example.diavantagemobile.ui.blood

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.ui.interfaces.MeasurementViewModel
import com.example.diavantagemobile.util.data.interfaces.BloodRepository
import com.example.diavantagemobile.util.data.responses.FailedSendBloodResponse
import com.example.diavantagemobile.util.data.responses.FailedSendGlucoseResponse
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BloodViewModel: MeasurementViewModel() {

    var bloodResponse by mutableStateOf<FailedSendBloodResponse?>(null)
        private set

    var inputSystolic by mutableStateOf("")
        private set

    var inputDiastolic by mutableStateOf("")
        private set

    var inputPulse by mutableStateOf("")
        private set

    fun updateSystolic(systolic: String){
        inputSystolic = systolic
    }

    fun updateDiastolic(diastolic: String){
        inputDiastolic = diastolic
    }

    fun updatePulse(pulse: String){
        inputPulse = pulse
    }

    override fun resetUserInput(){
        super.resetUserInput()
        inputSystolic = ""
        inputDiastolic = ""
        inputPulse = ""
    }

    fun sendBloodMeasurement(bloodRepository: BloodRepository, patient: String?) = runBlocking {
        val result = bloodRepository.sendBloodMeasurement(
            patient = patient,
            systolic = inputSystolic,
            diastolic = inputDiastolic,
            pulse = inputPulse,
            measurementDate = inputDate + "T" + inputTime
        )
        bloodResponse = result
        toggleDialog()
    }
}