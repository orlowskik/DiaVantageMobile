package com.example.diavantagemobile

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.diavantagemobile.DiaVantageDestinationsArgs.USER_MESSAGE_ARG
import com.example.diavantagemobile.DiaVantageScreens.HOME_SCREEN
import com.example.diavantagemobile.DiaVantageScreens.LOGIN_SCREEN


private object DiaVantageScreens {
    const val LOGIN_SCREEN = "login"
    const val HOME_SCREEN = "home"
}

object DiaVantageDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}


object DiaVantageDestinations {
    const val LOGIN_ROUTE = "$LOGIN_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val HOME_ROUTE = "$HOME_SCREEN?"
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

    fun navigateToHome() {
        navController.navigate(HOME_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}