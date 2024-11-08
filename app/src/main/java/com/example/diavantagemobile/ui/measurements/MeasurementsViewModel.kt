package com.example.diavantagemobile.ui.measurements

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.diavantagemobile.ui.interfaces.APIViewModel
import com.example.diavantagemobile.util.api.blood.BloodRepository
import com.example.diavantagemobile.util.api.glucose.GlucoseRepository
import com.example.diavantagemobile.util.api.responses.BloodResponse
import com.example.diavantagemobile.util.api.responses.GlucoseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class MeasurementsViewModel: APIViewModel() {

    val sortingMap = mapOf(
        0 to "Date descending",
        1 to "Date ascending"
    )

    private val _originalBloodMeasurements = MutableStateFlow(listOf<BloodResponse>())
    private val originalBloodMeasurements = _originalBloodMeasurements.asStateFlow()

    private val _originalGlucoseMeasurements = MutableStateFlow(listOf<GlucoseResponse>())
    private val originalGlucoseMeasurements = _originalGlucoseMeasurements.asStateFlow()

    private val _glucoseMeasurements = MutableStateFlow(listOf<GlucoseResponse>())
    val glucoseMeasurements = _glucoseMeasurements.asStateFlow()

    private val _bloodMeasurements = MutableStateFlow(listOf<BloodResponse>())
    val bloodMeasurements = _bloodMeasurements.asStateFlow()

    private val _filteringMapScheme = MutableStateFlow(createBooleanFilteringMap())
    val filteringMapScheme = _filteringMapScheme.asStateFlow()

    private val _filteringMap = MutableStateFlow(createFilteringMap())
    val filteringMap = _filteringMap.asStateFlow()

    private val _measurementsShowMap = MutableStateFlow(mutableMapOf(
        "Glucose" to true,
        "Blood" to true
    ))
    val measurementsShowMap = _measurementsShowMap.asStateFlow()

    var inputSorting by mutableIntStateOf(0)
        private set

    fun updateSorting(sorting: Int){
        inputSorting = sorting
    }

    fun sortMeasurements(){
        when (inputSorting){
            0 -> {
                _glucoseMeasurements.value = originalGlucoseMeasurements.value.sortedByDescending { it.measurement_date }
                _bloodMeasurements.value = originalBloodMeasurements.value.sortedByDescending { it.measurement_date }
            }
            1 -> {
                _glucoseMeasurements.value = originalGlucoseMeasurements.value.sortedBy { it.measurement_date }
                _bloodMeasurements.value = originalBloodMeasurements.value.sortedBy { it.measurement_date }
            }

        }
    }

    fun toggleMapIndex(category: String, option: String){
        _filteringMapScheme.value[category]?.set(option,
            !_filteringMapScheme.value[category]?.get(option)!!
        )
        Log.i("Toggle", _filteringMapScheme.value[category].toString())
    }

    fun reloadMeasurements(glucoseRepository: GlucoseRepository, bloodRepository: BloodRepository)
    = runBlocking {
        reloadGlucoseMeasurement(glucoseRepository)
        reloadBloodMeasurement(bloodRepository)
        sortMeasurements()
    }

    private fun createFilteringMap(): MutableMap<String, MutableSet<String>>{
        val measurementKinds = mutableSetOf<String>()

        return mutableMapOf(
            "Kind" to measurementKinds,
        )
    }

    private fun createBooleanFilteringMap(): MutableMap<String, MutableMap<String, Boolean>>{
        val measurementKinds = mutableSetOf("Glucose", "Blood")

        return mutableMapOf(
            "Kind" to measurementKinds.associateWith { false }.toMutableMap(),
        )
    }

    fun applyFiltering(){
        Log.i("Kind", _filteringMap.value["Kind"].toString())
        if (_filteringMap.value["Kind"] != null){
            if (_filteringMap.value["Kind"]?.isEmpty() == true) {
                _measurementsShowMap.value = mutableMapOf(
                    "Glucose" to true,
                    "Blood" to true
                )
            } else {
                _measurementsShowMap.value = mutableMapOf(
                    "Glucose" to false,
                    "Blood" to false
                )
                _filteringMap.value["Kind"]?.forEach {
                    _measurementsShowMap.value[it] = true
                }
            }
        } else {
            _measurementsShowMap.value = mutableMapOf(
                "Glucose" to true,
                "Blood" to true
            )
        }
    }


    private suspend fun reloadBloodMeasurement(bloodRepository: BloodRepository){
        try {
            val bloodMeasurements = bloodRepository.getBloodMeasurements()
            if (bloodMeasurements != null) {
                _originalBloodMeasurements.value = bloodMeasurements
            }
        } catch (e: Exception){
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }

    private suspend fun reloadGlucoseMeasurement(glucoseRepository: GlucoseRepository){
        try {
            val glucoseMeasurements = glucoseRepository.getGlucoseMeasurements()
            if (glucoseMeasurements != null) {
                _originalGlucoseMeasurements.value = glucoseMeasurements
            }
        } catch (e: Exception){
            errorName = e.javaClass.simpleName
            errorMessage = e.message
            toggleErrorDialog()
        }
    }


}