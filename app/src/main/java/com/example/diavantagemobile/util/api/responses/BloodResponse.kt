package com.example.diavantagemobile.util.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class BloodResponse(
    val systolic_pressure: Int,
    val diastolic_pressure: Int,
    val pulse_rate: Int,
    val measurement_date: String
){
    fun getMeasurementDate(): String{
        return measurement_date.replace('T', ' ')
    }
}
