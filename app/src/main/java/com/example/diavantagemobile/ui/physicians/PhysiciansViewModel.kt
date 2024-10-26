package com.example.diavantagemobile.ui.physicians

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diavantagemobile.ui.interfaces.APIViewModel
import com.example.diavantagemobile.util.api.physicians.PhysiciansRepository
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class PhysiciansViewModel : APIViewModel() {

    val sortingMap = mapOf(
        0 to "None",
        1 to "Name ascending",
        2 to "Name descending",
    )

    private val _filteringMapScheme = MutableStateFlow(createBooleanFilteringMap())
    val filteringMapScheme = _filteringMapScheme.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _originalPhysicians = MutableStateFlow(listOf<PhysicianResponse>())
    private val originalPhysicians = _originalPhysicians.asStateFlow()

    private val _filteringMap = MutableStateFlow(createFilteringMap())
    val filteringMap = _filteringMap.asStateFlow()

    private val _physicians = MutableStateFlow(listOf<PhysicianResponse>())
    @OptIn(FlowPreview::class)
    var physicians = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_physicians) { text, physicians ->
            if (text.isBlank()){
                physicians
            } else {
                physicians.filter{
                    it.matchesSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
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


    private fun createFilteringMap(): MutableMap<String, MutableSet<String>> {
        val specialties = mutableSetOf<String>()
        val countries = mutableSetOf<String>()
        val cities = mutableSetOf<String>()

        if(_originalPhysicians != null){
            _originalPhysicians.value.forEach{ physician ->
                specialties.add(physician.specialty)
                countries.add(physician.address.country)
                cities.add(physician.address.city)
            }
        }

        return mutableMapOf(
            "Specialty" to specialties,
            "Country" to countries,
            "City" to cities
        )
    }

    private fun createBooleanFilteringMap(): MutableMap<String, MutableMap<String, Boolean>>{
        val specialties = mutableSetOf<String>()
        val countries = mutableSetOf<String>()
        val cities = mutableSetOf<String>()

        if(_originalPhysicians != null){
            _originalPhysicians.value.forEach{ physician ->
                specialties.add(physician.specialty)
                countries.add(physician.address.country)
                cities.add(physician.address.city)
            }
        }

        return mutableMapOf(
            "Specialty" to specialties.associateWith { false }.toMutableMap(),
            "Country" to countries.associateWith { false }.toMutableMap(),
            "City" to cities.associateWith { false }.toMutableMap()
        )
    }


    fun applyFiltering(updatedMap: MutableMap<String,MutableSet<String>>){
        updateFilteringMap(updatedMap)
        Log.i("Filtering", _filteringMap.value.toString())
    }


    private fun updateFilteringMap(updatedMap: MutableMap<String,MutableSet<String>>){
        _filteringMap.value = updatedMap
    }

    fun toggleMapIndex(category: String, option: String){
        _filteringMapScheme.value[category]?.set(option,
            !_filteringMapScheme.value[category]?.get(option)!!
        )
        Log.i("Toggle", _filteringMapScheme.value[category].toString())
    }

    fun reloadPhysicians(physiciansRepository: PhysiciansRepository) = runBlocking{
        try {
            val result = physiciansRepository.getPhysicians()
            _originalPhysicians.value = result
            sortPhysicians()
            _filteringMapScheme.value = createBooleanFilteringMap()
        } catch (e: Exception) {
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }
}
