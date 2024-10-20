package com.example.diavantagemobile.util.api.responses

import kotlinx.serialization.Serializable


@Serializable
data class  AddressSerializer(
    val country: String,
    val state: String,
    val city: String,
    val street: String,
    val zip_code: String,
    val number: String,
    val apartment: String?,
)
