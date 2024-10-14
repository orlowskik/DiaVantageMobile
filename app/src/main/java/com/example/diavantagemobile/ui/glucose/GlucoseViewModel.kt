package com.example.diavantagemobile.ui.glucose

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GlucoseViewModel : ViewModel(){

    var inputGlucose by mutableStateOf("")
        private set

    var inputDate by mutableStateOf("")
        private set

    var inputTime by mutableStateOf("")
        private set

    fun updateGlucose(glucose: String){
        inputGlucose = glucose
    }

    fun updateDate(millis: Long?){
        inputDate = millis?.let { convertMillisToDate(it) } ?: ""
        Log.i("Set date", inputDate)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateTime(time: TimePickerState?) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, time!!.hour)
        cal.set(Calendar.MINUTE, time!!.minute)
        cal.isLenient = false
        Log.i("Set time", cal.time.toString())
        inputTime = cal.time.toString()

    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }


    fun resetUserInput(){
       inputGlucose = ""
       inputDate = ""
       inputTime = ""
    }

}