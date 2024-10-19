package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.FailedSendBloodResponse


interface BloodRepository {
    suspend fun sendBloodMeasurement(
        patient: String?,
        systolic: String,
        diastolic: String,
        pulse: String,
        measurementDate: String,
    ) : FailedSendBloodResponse?
}