package com.example.diavantagemobile.util.data


const val local: Boolean = true

data class ApiStrings(
    val hostName: String = if(local) "http://192.168.1.56:8000/" else "https://diavantage.azurewebsites.net/",
    val login: String = "${hostName}rest-auth/login/",
    val logout: String = "${hostName}rest-auth/logout/",
    val getCSRF: String = "${hostName}get_csrf/",
    val patientRegister: String = "${hostName}register/patient/",
    val checkPatient: String = "${hostName}api/patients/search_patient/",
    val glucose: String = "${hostName}api/glucose/"
)
