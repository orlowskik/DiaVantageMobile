package com.example.diavantagemobile

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diavantagemobile.ui.home.HomeScreen
import com.example.diavantagemobile.ui.login.LoginScreen
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.CoroutineScope


@Composable
fun DiaVantageApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = DiaVantageDestinations.LOGIN_ROUTE,
    navActions: DiaVantageNavigationActions = remember(navController) {
        DiaVantageNavigationActions(navController)
    },
    api: DiaVantageApi = DiaVantageApi(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(DiaVantageDestinations.LOGIN_ROUTE) {
            LoginScreen(
                modifier = modifier,
                api = api,
                onSuccessfulLogin = fun (success: Boolean) { if (success) {
                    navActions.navigateToHome()
                } else{
                    navActions.navigateToLogin()
                }},
            )
        }
        composable(DiaVantageDestinations.HOME_ROUTE) {
            HomeScreen(
                api = api,
                modifier = modifier,
                onLogoutButtonPressed = fun (success : Boolean) { if (success) {
                    navActions.navigateToLogin()
                } else{
                    Log.e("Error", "Logout failed")
                }}
            )
        }
    }
}