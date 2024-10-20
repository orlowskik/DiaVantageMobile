package com.example.diavantagemobile.ui.physicians

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.physicians.PhysiciansRepository
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import com.example.diavantagemobile.util.data.TopAppBarTypes
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PhysiciansScreen(
    physiciansViewModel: PhysiciansViewModel = PhysiciansViewModel(),
    physiciansRepository: PhysiciansRepository,
    modifier: Modifier = Modifier,
    returnToHome: () -> Unit
){

    LaunchedEffect(true){
        physiciansViewModel.reloadPhysicians(physiciansRepository)
        Log.i("Physicians", physiciansViewModel.physicians.toString())

    }



    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Physicians",
                appBarType = TopAppBarTypes.CenterAlignedTopAppBar,
                actions = {},
                navigationIcon = {
                    IconButton (onClick = { returnToHome() } ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = modifier
            )
        },
        content = {
            PhysiciansContentLayout(
                physicians = physiciansViewModel.physicians,
                modifier = modifier
            )
        }
    )
}

@Composable
fun PhysiciansContentLayout(
    physicians: List<PhysicianResponse?>?,
    modifier: Modifier = Modifier
){

}