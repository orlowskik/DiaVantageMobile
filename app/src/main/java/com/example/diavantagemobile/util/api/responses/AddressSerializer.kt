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
){
    fun createAddress(): String {
        return "ul. $street $number${if (apartment != null) "/$apartment" else ""}, $city $zip_code,\n$state, $country "
    }
}
