package com.example.diavantagemobile.util.api.physicians

import com.example.diavantagemobile.util.api.responses.PhysicianResponse

interface PhysiciansRepository {
    suspend fun getPhysicians(): List<PhysicianResponse?>?
}