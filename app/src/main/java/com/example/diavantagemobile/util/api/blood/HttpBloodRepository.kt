package com.example.diavantagemobile.util.api.blood

import android.util.Log
import com.example.diavantagemobile.util.api.responses.BloodResponse
import com.example.diavantagemobile.util.data.ApiStrings
import com.example.diavantagemobile.util.api.responses.CsrfResponse
import com.example.diavantagemobile.util.api.responses.FailedSendBloodResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters

class HttpBloodRepository(
    private val client: HttpClient,
    private val apiStrings: ApiStrings = ApiStrings(),
): BloodRepository {
    override suspend fun sendBloodMeasurement(
        patient: String?,
        systolic: String,
        diastolic: String,
        pulse: String,
        measurementDate: String
    ): FailedSendBloodResponse? {
        Log.i("Blood Send", "Starting blood send")
        Log.i("Blood Send", patient.toString())
        val token = client.post(apiStrings.getCSRF).body<CsrfResponse>()

        val response = client.submitForm(
            url = apiStrings.blood,
            formParameters = parameters {
                append("patient", patient.toString())
                append("systolic_pressure", systolic)
                append("diastolic_pressure", diastolic)
                append("pulse_rate", pulse)
                append("measurement_date", measurementDate)
                append("csrfmiddlewaretoken", token.CSRFToken)
            }
        ){
            cookie(name = "csrftoken", value = token.CSRFToken)
            header("X-CSRFToken",token.CSRFToken)
        }
        if (response.status == HttpStatusCode.Created){
            return null
        }

        Log.i("Glucose Send", response.status.toString())
        Log.i("Glucose Send", response.body())

        return response.body<FailedSendBloodResponse?>()
    }

    override suspend fun getBloodMeasurements(): List<BloodResponse>? {
        Log.i("Blood retrieve", "Starting blood retrieve")

        val response = client.get(apiStrings.blood)

        return if(response.status == HttpStatusCode.OK)
            response.body<List<BloodResponse>>()
        else
            null
    }
}