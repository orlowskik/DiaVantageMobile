package com.example.diavantagemobile.ui.interfaces

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class APIViewModel: ViewModel() {

    var showErrorDialog by mutableStateOf(false)
        private set

    var errorName by mutableStateOf<String?>(null)
        protected set

    var errorMessage by mutableStateOf<String?>(null)
        protected set

    fun toggleErrorDialog() {
        showErrorDialog = !showErrorDialog
    }

    fun updateErrorMessage(message: String?) {
        errorMessage = message
    }
    fun updateErrorName(name: String?) {
        errorName = name

    }

    fun resetError() {
        errorName = null
        errorMessage = null
    }



}