package com.example.diavantagemobile.ui.blood

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.diavantagemobile.ui.interfaces.MeasurementViewModel
import com.example.diavantagemobile.util.api.blood.BloodRepository
import com.example.diavantagemobile.util.api.responses.FailedSendBloodResponse
import kotlinx.coroutines.runBlocking

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