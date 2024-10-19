package com.example.diavantagemobile.ui.interfaces


import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

open class MeasurementViewModel : APIViewModel() {
    private val cal = Calendar.getInstance()
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


    var showCreationDialog by mutableStateOf(false)
        private set

    var inputDate by mutableStateOf<String>(dateFormatter.format(cal.timeInMillis))
        private set

    var inputTime by mutableStateOf<String>(timeFormatter.format(cal.time))
        private set


    fun toggleDialog(){
        showCreationDialog = !showCreationDialog
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateTime(time: TimePickerState?) {
        val cal = Calendar.getInstance()

        cal.set(Calendar.HOUR_OF_DAY, time!!.hour)
        cal.set(Calendar.MINUTE, time.minute)
        cal.isLenient = false

        inputTime = timeFormatter.format(cal.time)
        Log.i("Set time", inputTime)

    }


    private fun convertMillisToDate(millis: Long): String {
        return dateFormatter.format(Date(millis))
    }

    fun updateDate(millis: Long?){
        inputDate = millis?.let { convertMillisToDate(it) } ?: ""
        Log.i("Set date", inputDate)
    }

    open fun resetUserInput(){
        val cal = Calendar.getInstance()
        inputDate = dateFormatter.format(Date(cal.timeInMillis))
        inputTime = timeFormatter.format(cal.time)
    }


}