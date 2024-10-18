package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.SendGlucoseResponse

interface GlucoseRepository {
    suspend fun sendGlucoseMeasurement(
        patient: String?,
        measurement: String,
        measurementType: String,
        measurementDate: String
    ): SendGlucoseResponse?
}