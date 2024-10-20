package com.example.diavantagemobile.util.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class PhysicianResponse(
    val user: UserSerializer,
    val address: AddressSerializer,
    val specialty: String,
    val phone: String,
)
