package com.example.diavantagemobile.util.api.responses

import com.example.diavantagemobile.util.data.GlucoseTypes
import kotlinx.serialization.Serializable

@Serializable
data class GlucoseResponse(
    val measurement: Double,
    val measurement_type: Int,
    val measurement_date: String,
){
    val glucoseTypes = GlucoseTypes().map

    fun getMeasurementType(): String{
        glucoseTypes[measurement_type]?.let {
            return it
        } ?: return "Undefined"
    }

    fun getMeasurementDate(): String{
        return measurement_date.replace('T', ' ')
    }
}
