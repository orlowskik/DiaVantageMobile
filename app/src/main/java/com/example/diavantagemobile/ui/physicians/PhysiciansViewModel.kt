package com.example.diavantagemobile.ui.physicians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.diavantagemobile.ui.interfaces.APIViewModel
import com.example.diavantagemobile.util.api.physicians.PhysiciansRepository
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import kotlinx.coroutines.runBlocking

class PhysiciansViewModel : APIViewModel() {
    var physicians by mutableStateOf<List<PhysicianResponse?>?>(null)
        private set

    fun reloadPhysicians(physiciansRepository: PhysiciansRepository) = runBlocking{
        try {
            val result = physiciansRepository.getPhysicians()
            physicians = result
        } catch (e: Exception) {
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }
}