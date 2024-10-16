package com.example.diavantagemobile.util.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable


const val local: Boolean = true

data class ApiStrings(
    val hostName: String = if(local) "http://192.168.1.56:8000/" else "https://diavantage.azurewebsites.net/",
    val login: String = "rest-auth/login/",
    val logout: String = "rest-auth/logout/",
    val patientRegister: String = "register/patient/",
    val checkPatient: String = "api/patients/search_patient/"
    )

@Serializable
data class AuthToken(
    val key: String,
)

@Serializable
data class Patient(
    val patient_id: Int?,
)

class DiaVantageApi(
    private val httpClient: HttpClient = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
        install(HttpCookies)
    },
    val apiStrings: ApiStrings = ApiStrings(),
    private var patientId: Int? = null,
    private var authToken: AuthToken? = null
) {


    fun isPatient(): Boolean{
        patientId?.let { return true } ?: return false
    }

    fun authenticate(username: String, password: String): Boolean = runBlocking {
        Log.i("Authentication", "Starting login")
        val loginState = async { login(username, password) }.await()

        if (loginState) {
            val patientCheckState = async { checkPatient() }.await()

            if (patientCheckState){
                return@runBlocking true
            }
        }
        return@runBlocking false
    }


    private suspend fun login(username: String, password: String): Boolean{
        Log.i("Send Credentials", "Sending credentials")
        val endpoint = apiStrings.hostName + apiStrings.login
        val response: HttpResponse = httpClient.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("username", username)
                append("password", password)
            }
        )
        Log.i("Send Credentials", response.status.toString())
        Log.i("Send Credentials", response.body())
        if (response.status == HttpStatusCode.OK) {
            authToken =  response.body()
            updateClientCredentials(username, password)
            Log.i("Send Credentials", "End")
            return true
        }
        Log.i("Send Credentials", "End")
        return false
    }



    private suspend fun checkPatient(): Boolean {
        Log.i("Checking Patient", "Start")

        val endpoint = apiStrings.hostName + apiStrings.checkPatient
        val response: HttpResponse = httpClient.get(endpoint)

        Log.i("Checking Patient", response.status.toString())
        Log.i("Checking Patient", response.body())

        if (response.status == HttpStatusCode.OK) {
            val responseData: Patient = response.body()
            patientId = responseData.patient_id
            Log.i("Checking Patient", "End")
            return true
        }
        Log.i("Checking Patient", "End")
        return false
    }


    suspend fun logout(): Boolean{
        Log.i("Logout", "Starting logout")
        val endpoint = apiStrings.hostName + apiStrings.logout
        val response: HttpResponse = httpClient.post(endpoint)
        Log.i("Logout", response.status.toString())
        Log.i("Logout", response.body())

        if (response.status == HttpStatusCode.OK) {
            updateClientCredentials("", "")
            Log.i("Logout", "Successful")
            return true
        }
        Log.i("Logout", "Failed")
        return false
    }

    private fun updateClientCredentials(username: String, password: String) {
        httpClient.config {
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = username, password = password)
                    }
                }
            }
        }
    }


    fun sendGlucoseMeasurement(measurement: Long, measurementType: String, measurementDate: String){

    }
}
