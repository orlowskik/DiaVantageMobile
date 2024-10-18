package com.example.diavantagemobile.util.api

import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import com.example.diavantagemobile.util.data.interfaces.RemoteRepository
import com.example.diavantagemobile.util.data.storages.BasicCredentialStorage

object ID {
    private val credentialsStorage: CredentialsStorage = BasicCredentialStorage()
    private val httpClientFactory: HttpClientFactory = HttpClientFactory(credentialsStorage)
    val remoteRepository: RemoteRepository = RealRemoteRepository(httpClientFactory.create(), credentialsStorage)
}