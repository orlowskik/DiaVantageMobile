package com.example.diavantagemobile.util.api.credentials

data class BasicCredentials(
    private var username: String,
    private var password: String,
): Credentials() {
    override fun getCredentialsList(): List<String> {
        return listOf(username, password)
    }
}
