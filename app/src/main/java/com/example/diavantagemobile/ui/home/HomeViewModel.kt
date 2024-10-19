package com.example.diavantagemobile.ui.home

import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.api.login.LogoutRepository
import kotlinx.coroutines.runBlocking

class HomeViewModel: ViewModel() {
    fun logoutUser(logoutRepository: LogoutRepository) = runBlocking {
        return@runBlocking logoutRepository.logout()
    }
}