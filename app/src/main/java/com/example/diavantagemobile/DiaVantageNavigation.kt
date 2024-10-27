package com.example.diavantagemobile

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.diavantagemobile.DiaVantageDestinationsArgs.USER_MESSAGE_ARG
import com.example.diavantagemobile.DiaVantageScreens.BLOOD_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.GLUCOSE_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.HOME_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.LOGIN_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.MEASUREMENTS_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.PHYSICIANS_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.PHYSICIAN_HOME_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.REGISTRATION_SCREEN
import com.example.diavantagemobile.ui.blood.BloodScreen


private object DiaVantageScreens {
    const val LOGIN_SCREEN = "login"
    const val HOME_SCREEN = "home"
    const val PHYSICIAN_HOME_SCREEN = "physician_home"
    const val REGISTRATION_SCREEN = "registration"
    const val GLUCOSE_SCREEN = "glucose"
    const val BLOOD_SCREEN = "blood"
    const val PHYSICIANS_SCREEN = "physicians"
    const val MEASUREMENTS_SCREEN = "measurements"
}

object DiaVantageDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
}

object DiaVantageDestinations {
    const val LOGIN_ROUTE = "$LOGIN_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val HOME_ROUTE = "$HOME_SCREEN?"
    const val REGISTRATION_ROUTE = "$REGISTRATION_SCREEN?"
    const val GLUCOSE_ROUTE = "$GLUCOSE_SCREEN?"
    const val BLOOD_ROUTE = "$BLOOD_SCREEN?"
    const val PHYSICIANS_ROUTE = "$PHYSICIANS_SCREEN?"
    const val MEASUREMENTS_ROUTE = "$MEASUREMENTS_SCREEN?"
    const val PHYSICIAN_HOME_ROUTE = "$PHYSICIAN_HOME_SCREEN?"
}

class DiaVantageNavigationActions(private val navController: NavHostController) {

    fun navigateToLogin(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            LOGIN_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToRegistration() {
        navController.navigate(REGISTRATION_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToHome() {
        navController.navigate(HOME_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToPhysicianHome() {
        navController.navigate(PHYSICIAN_HOME_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToGlucose(){
        navController.navigate(GLUCOSE_SCREEN){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToBlood(){
        navController.navigate(BLOOD_SCREEN){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToMeasurements(){
        navController.navigate(MEASUREMENTS_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToPhysicians(){
        navController.navigate(PHYSICIANS_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}