package com.example.diavantagemobile.util.api.glucose

import com.example.diavantagemobile.util.api.responses.FailedSendGlucoseResponse
import com.example.diavantagemobile.util.api.responses.GlucoseResponse


interface GlucoseRepository {
    suspend fun sendGlucoseMeasurement(
        patient: String?,
        measurement: String,
        measurementType: String,
        measurementDate: String
    ): FailedSendGlucoseResponse?

    suspend fun getGlucoseMeasurements(): List<GlucoseResponse>?
}