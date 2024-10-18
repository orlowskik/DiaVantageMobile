package com.example.diavantagemobile.util.api

import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import io.ktor.client.HttpClient
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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


internal class HttpClientFactory (
    credentialsStorage: CredentialsStorage,
) {
    private val credentialsList = credentialsStorage.getCredentials().getCredentialsList()

    fun create(): HttpClient{
        return HttpClient{
            install(ContentNegotiation){
                json(
                    Json{
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }
            install(HttpCookies)
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = credentialsList[0], password = credentialsList[1])
                    }
                }
            }
        }
    }
}