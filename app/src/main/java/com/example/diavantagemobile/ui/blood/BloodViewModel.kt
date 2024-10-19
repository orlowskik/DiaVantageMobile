package com.example.diavantagemobile.ui.blood

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.data.interfaces.BloodRepository
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BloodViewModel: ViewModel() {
    var inputSystolic by mutableStateOf("")
        private set

    var inputDiastolic by mutableStateOf("")
        private set

    var inputPulse by mutableStateOf("")
        private set

    var inputDate by mutableStateOf("")
        private set

    var inputTime by mutableStateOf("")
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

    fun updateDate(millis: Long?){
        inputDate = millis?.let { convertMillisToDate(it) } ?: ""
        Log.i("Set date", inputDate)
    }

    @SuppressLint("SimpleDateFormat")
    @OptIn(ExperimentalMaterial3Api::class)
    fun updateTime(time: TimePickerState?) {
        val cal = Calendar.getInstance()
        val formatter = SimpleDateFormat("HH:mm")

        cal.set(Calendar.HOUR_OF_DAY, time!!.hour)
        cal.set(Calendar.MINUTE, time.minute)
        cal.isLenient = false

        inputTime = formatter.format(cal.time)
        Log.i("Set time", inputTime)

    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }


    fun resetUserInput(){
        inputSystolic = ""
        inputDiastolic = ""
        inputPulse = ""
        inputDate = ""
        inputTime = ""
    }

    fun sendBloodMeasurement(bloodRepository: BloodRepository, patient: String?) = runBlocking {
        val result = bloodRepository.sendBloodMeasurement(
            patient = patient,
            systolic = inputSystolic,
            diastolic = inputDiastolic,
            pulse = inputPulse,
            measurementDate = inputDate + "T" + inputTime
        )
    }
}