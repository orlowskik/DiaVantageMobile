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
import com.example.diavantagemobile.util.data.interfaces.GlucoseRepository
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.EnumMap
import java.util.EnumSet
import java.util.Locale


class GlucoseViewModel : ViewModel(){

    val typesMap = mapOf(
        0 to "undefined",
        1 to "Before Breakfast",
        2 to "After Breakfast",
        3 to "Before Lunch",
        4 to "After Lunch",
        5 to "Before Dinner",
        6 to "After Dinner"
    )


    var inputGlucose by mutableStateOf("")
        private set

    var inputType by mutableIntStateOf(0)
        private set

    var inputDate by mutableStateOf("")
        private set

    var inputTime by mutableStateOf("")
        private set

    fun updateGlucose(glucose: String){
        inputGlucose = glucose
    }

    fun updateType(type: Int){
        inputType = type
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
        cal.set(Calendar.MINUTE, time!!.minute)
        cal.isLenient = false

        inputTime = formatter.format(cal.time)
        Log.i("Set time", inputTime)

    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun resetUserInput(){
        inputGlucose = ""
        inputType = 0
        inputDate = ""
        inputTime = ""
    }

    fun sendGlucoseMeasurement(glucoseRepository: GlucoseRepository, patient: String?) = runBlocking{
        val result = glucoseRepository.sendGlucoseMeasurement(
            patient = "11",
            measurement = inputGlucose,
            measurementType = inputType.toString(),
            measurementDate = inputDate

        )
    }
}