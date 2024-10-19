package com.example.diavantagemobile.util.data.responses

import androidx.compose.runtime.key
import kotlinx.serialization.Serializable
import kotlin.reflect.full.memberProperties


@Serializable
data class LoginResponse(
    val key: String? = null,
)

@Serializable
data class LogoutResponse(
    val detail: String,
)

@Serializable
data class CheckPatientResponse(
    val patient_id: String? = null,
)

@Serializable
data class CsrfResponse(
    val detail: String,
    val CSRFToken : String,
)

@Serializable
data class FailedSendGlucoseResponse(
    val patient: List<String>? = null,
    val measurement: List<String>? = null,
    val measurement_type: List<String>? = null,
    val measurement_date: List<String>? = null,
){
    fun getFieldsKeys(): List<String>{
        return FailedSendGlucoseResponse::class.memberProperties.map { it.name }.toList()
    }
}

@Serializable
data class FailedSendBloodResponse(
    val patient: List<String>? = null,
    val systolic_pressure: List<String>? = null,
    val diastolic_pressure: List<String>? = null,
    val pulse_rate: List<String>? = null,
    val measurement_date: List<String>? = null,
){
    fun getFieldsKeys(): List<String>{
        return FailedSendBloodResponse::class.memberProperties.map { it.name }.toList()
    }

}