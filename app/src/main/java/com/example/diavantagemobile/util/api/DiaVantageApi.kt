package com.example.diavantagemobile.util.api

import android.util.Log
import com.example.diavantagemobile.util.data.ApiStrings
import com.example.diavantagemobile.util.data.responses.CheckPatientResponse
import com.example.diavantagemobile.util.data.credentials.BasicCredentials
import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import com.example.diavantagemobile.util.data.storages.BasicCredentialStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class DiaVantageApi(
    private val httpClient: HttpClient = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
        install(Logging){
            logger = Logger.SIMPLE
            level = LogLevel.HEADERS
        }
        install(HttpCookies)
    },
    private val credentialsStorage: CredentialsStorage = BasicCredentialStorage(),
    val apiStrings: ApiStrings = ApiStrings(),
    private var patientId: String? = null,
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
            return@runBlocking true
        }
        return@runBlocking false
    }


    private suspend fun login(username: String, password: String): Boolean{
        Log.i("Send Credentials", "Sending credentials")
        val endpoint = apiStrings.login
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
            updateClientCredentials(username, password)
            credentialsStorage.putCredentials(BasicCredentials(username, password))
            Log.i("Send Credentials", "End")
            return true
        }
        Log.i("Send Credentials", "End")
        return false
    }



    private suspend fun checkPatient(): Boolean {
        Log.i("Checking Patient", "Start")

        val endpoint = apiStrings.checkPatient
        val response: HttpResponse = httpClient.get(endpoint)

        Log.i("Checking Patient", response.status.toString())
        Log.i("Checking Patient", response.body())

        if (response.status == HttpStatusCode.OK) {
            val responseData: CheckPatientResponse = response.body()
            patientId = responseData.patient_id
            Log.i("Checking Patient", "End")
            return true
        }
        Log.i("Checking Patient", "End")
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

}
