package com.example.diavantagemobile.ui.home

import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.data.interfaces.LogoutRepository
import kotlinx.coroutines.runBlocking

class HomeViewModel: ViewModel() {
    fun logoutUser(logoutRepository: LogoutRepository) = runBlocking {
        return@runBlocking logoutRepository.logout()
    }
}