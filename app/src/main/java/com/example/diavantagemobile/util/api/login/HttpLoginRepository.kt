package com.example.diavantagemobile.util.api.login

import android.util.Log
import com.example.diavantagemobile.util.data.responses.LoginResponse
import com.example.diavantagemobile.util.data.credentials.BasicCredentials
import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import com.example.diavantagemobile.util.data.interfaces.LoginRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters

class HttpLoginRepository(
    private val client: HttpClient,
    private val credentialStorage: CredentialsStorage,
    private val endpoint: String,
) : LoginRepository{
    override suspend fun login(username: String, password: String): LoginResponse? {
        Log.i("Send Credentials", "Sending credentials")
        val response: HttpResponse = client.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("username", username)
                append("password", password)
            }
        )
        Log.i("Send Credentials", response.status.toString())
        Log.i("Send Credentials", response.body())

        return response.body<LoginResponse?>()
            .also {
                if (it != null){
                    credentialStorage.putCredentials(BasicCredentials(username, password))
                }
            }
    }
}