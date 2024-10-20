package com.example.diavantagemobile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diavantagemobile.ui.blood.BloodScreen
import com.example.diavantagemobile.ui.glucose.GlucoseScreen
import com.example.diavantagemobile.ui.home.HomeScreen
import com.example.diavantagemobile.ui.home.PhysicianHomeScreen
import com.example.diavantagemobile.ui.login.LoginScreen
import com.example.diavantagemobile.ui.physicians.PhysiciansScreen
import com.example.diavantagemobile.ui.registration.RegistrationScreen
import com.example.diavantagemobile.util.LoginUserInfo
import com.example.diavantagemobile.util.api.ID
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DiaVantageApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DiaVantageDestinations.LOGIN_ROUTE,
    navActions: DiaVantageNavigationActions = remember(navController) {
        DiaVantageNavigationActions(navController)
    },
) {



    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(DiaVantageDestinations.LOGIN_ROUTE) {
            LoginScreen(
                modifier = modifier,
                loginRepository = ID.remoteRepository.loginRepository(),
                checkPatientRepository = ID.remoteRepository.checkPatientRepository(),
                onRegisterButtonPressed = { navActions.navigateToRegistration() },
                onSuccessfulLogin = fun (success: Boolean, username: String?, password: String?, patientId: String?) { if (success) {
                    LoginUserInfo.userInfo.updateUserInfo(username, password, patientId)
                    Log.i("Login", LoginUserInfo.userInfo.getInfo().toString())

                     if (LoginUserInfo.userInfo.getDistinctInfo("patientId") != null) navActions.navigateToHome() else navActions.navigateToPhysicianHome()
                } },
            )
        }
        composable(DiaVantageDestinations.REGISTRATION_ROUTE) {
            RegistrationScreen(
                modifier = modifier,
                returnToLogin = { navActions.navigateToLogin() }
            )
        }

        composable(DiaVantageDestinations.PHYSICIAN_HOME_ROUTE){
            PhysicianHomeScreen(
                modifier = modifier,
                onAccountInfoButtonPressed = {},
                onLogoutButtonPressed = fun(success: Boolean) {
                    if (success) {
                        LoginUserInfo.userInfo.updateUserInfo(null, null, null)
                        Log.i("Logout", LoginUserInfo.userInfo.getInfo().toString())
                        navActions.navigateToLogin()
                    } else {
                        Log.e("Error", "Logout failed")
                    }
                },
                logoutRepository = ID.remoteRepository.logoutRepository(),
            )
        }
        composable(DiaVantageDestinations.HOME_ROUTE) {
            HomeScreen(
                logoutRepository = ID.remoteRepository.logoutRepository(),
                modifier = modifier,
                onLogoutButtonPressed = fun(success: Boolean) {
                    if (success) {
                        LoginUserInfo.userInfo.updateUserInfo(null, null, null)
                        Log.i("Logout", LoginUserInfo.userInfo.getInfo().toString())
                        navActions.navigateToLogin()
                    } else {
                        Log.e("Error", "Logout failed")
                    }
                },
                onAccountInfoButtonPressed = {},
                onGlucosePress = { navActions.navigateToGlucose() },
                onBloodPress = { navActions.navigateToBlood() },
                onPhysiciansPress = { navActions.navigateToPhysicians() },
                onHistoryPress = {},
            )
        }
        composable(DiaVantageDestinations.GLUCOSE_ROUTE) {
            GlucoseScreen(
                glucoseRepository = ID.remoteRepository.glucoseRepository(),
                patientId = LoginUserInfo.userInfo.getDistinctInfo("patientId"),
                modifier = modifier,

                returnToHome = { navActions.navigateToHome() }
            )
        }

        composable(DiaVantageDestinations.BLOOD_ROUTE) {
            BloodScreen(
                bloodRepository =ID.remoteRepository.bloodRepository(),
                patientId = LoginUserInfo.userInfo.getDistinctInfo("patientId"),
                modifier = modifier,
                returnToHome = {navActions.navigateToHome()}
            )
        }

        composable(DiaVantageDestinations.PHYSICIANS_ROUTE) {
            PhysiciansScreen(
                modifier = modifier,
                physiciansRepository = ID.remoteRepository.physiciansRepository(),
                returnToHome = {navActions.navigateToHome()}
            )
        }

    }
}