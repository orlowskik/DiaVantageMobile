package com.example.diavantagemobile.util.api.glucose

import android.util.Log
import com.example.diavantagemobile.util.data.ApiStrings
import com.example.diavantagemobile.util.data.interfaces.GlucoseRepository
import com.example.diavantagemobile.util.data.responses.CsrfResponse
import com.example.diavantagemobile.util.data.responses.SendGlucoseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.parameters

class HttpGlucoseRepository(
    private val client: HttpClient,
    private val apiStrings: ApiStrings = ApiStrings(),
): GlucoseRepository {
    override suspend fun sendGlucoseMeasurement(
        patient: String?,
        measurement: String,
        measurementType: String,
        measurementDate: String
    ): SendGlucoseResponse? {
        Log.i("Glucose Send", "Starting glucose send")
        Log.i("Glucose Send", patient.toString())
        if (patient == null) return null
        val token = client.post(apiStrings.getCSRF).body<CsrfResponse>()
        val response = client.submitForm(
            url = apiStrings.glucose,
            formParameters = parameters {
                append("patient", patient)
                append("measurement", measurement)
                append("measurement_type", measurementType)
                append("measurement_date", measurementDate)
                append("csrfmiddlewaretoken", token.CSRFToken)
            }
        ){
            cookie(name = "csrftoken", value = token.CSRFToken)
            header("X-CSRFToken",token.CSRFToken)
        }

        Log.i("Glucose Send", response.status.toString())
        Log.i("Glucose Send", response.body())

        return response.body<SendGlucoseResponse?>()

    }
}