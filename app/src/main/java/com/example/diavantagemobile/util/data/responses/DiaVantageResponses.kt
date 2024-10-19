package com.example.diavantagemobile.util.data.responses

import kotlinx.serialization.Serializable


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
data class SendGlucoseResponse(
    val patient: String,
    val measurement: String,
    val measurement_type: String,
    val measurement_date: String,
)

@Serializable
data class SendBloodResponse(
    val patient: String,
    val systolic_pressure: String,
    val diastolic_pressure: String,
    val pulse_rate: String,
    val measurement_date: String,
)