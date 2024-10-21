package com.example.diavantagemobile.ui.physicians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diavantagemobile.ui.interfaces.APIViewModel
import com.example.diavantagemobile.util.api.physicians.PhysiciansRepository
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class PhysiciansViewModel : APIViewModel() {

    val sortingMap = mapOf(
        0 to "None",
        1 to "Name ascending",
        2 to "Name descending",
    )

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _originalPhysicians = MutableStateFlow(listOf<PhysicianResponse>())
    val originalPhysicians = _originalPhysicians.asStateFlow()

    private val _physicians = MutableStateFlow(listOf<PhysicianResponse>())
    var physicians = searchText
        .combine(_physicians) { text, physicians ->
            if (text.isBlank()){
                physicians
            } else {
                physicians.filter{
                    it.matchesSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _physicians.value
        )

    var inputSorting by mutableIntStateOf(0)
        private set


    fun updateSorting(sorting: Int){
        inputSorting = sorting
    }

    fun sortPhysicians(){
        when (inputSorting){
            0 -> _physicians.value = _originalPhysicians.value.toList()
            1 -> _physicians.value = originalPhysicians.value.sortedBy { it.user.first_name?.lowercase() }
            2 -> _physicians.value = originalPhysicians.value.sortedByDescending { it.user.first_name?.lowercase() }
        }
    }

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }

    fun reloadPhysicians(physiciansRepository: PhysiciansRepository) = runBlocking{
        try {
            val result = physiciansRepository.getPhysicians()
            _originalPhysicians.value = result
            sortPhysicians()
        } catch (e: Exception) {
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }
}
