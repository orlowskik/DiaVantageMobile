package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.FailedSendGlucoseResponse


interface GlucoseRepository {
    suspend fun sendGlucoseMeasurement(
        patient: String?,
        measurement: String,
        measurementType: String,
        measurementDate: String
    ): FailedSendGlucoseResponse?
}