package com.example.diavantagemobile.util.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class PhysicianResponse(
    val user: UserSerializer,
    val address: AddressSerializer,
    val specialty: String,
    val phone: String,
){
    fun matchesSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            "${user.first_name}${user.last_name}",
            "${user.first_name} ${user.last_name}",
        )

        return matchingCombinations.any {
            it.contains(
                query,
                ignoreCase = true
            )
        }

    }
}
