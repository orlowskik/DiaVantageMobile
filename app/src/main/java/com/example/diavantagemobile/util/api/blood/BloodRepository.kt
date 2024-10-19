package com.example.diavantagemobile.util.api.blood

import com.example.diavantagemobile.util.api.responses.FailedSendBloodResponse


interface BloodRepository {
    suspend fun sendBloodMeasurement(
        patient: String?,
        systolic: String,
        diastolic: String,
        pulse: String,
        measurementDate: String,
    ) : FailedSendBloodResponse?
}