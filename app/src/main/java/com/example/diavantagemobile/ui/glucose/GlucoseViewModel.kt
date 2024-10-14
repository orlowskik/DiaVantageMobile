package com.example.diavantagemobile.ui.glucose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class GlucoseViewModel : ViewModel(){

    var inputGlucose by mutableIntStateOf(0)
        private set

    var inputDate by mutableStateOf("")
        private set

    var inputTime by mutableStateOf("")
        private set

    fun updateGlucose(glucose: Int){
        inputGlucose = glucose
    }

    fun updateDate(millis: Long?){
        inputDate = millis?.let { convertMillisToDate(it) } ?: ""
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }


    fun resetUserInput(){
       inputGlucose = 0
       inputDate = ""
       inputTime = ""
    }

}