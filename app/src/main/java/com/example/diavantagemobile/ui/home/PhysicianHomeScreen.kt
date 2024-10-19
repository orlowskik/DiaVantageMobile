package com.example.diavantagemobile.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.DiaVantageApi
import com.example.diavantagemobile.util.api.ID
import com.example.diavantagemobile.util.data.TopAppBarTypes
import com.example.diavantagemobile.util.api.login.LogoutRepository

@Composable
fun PhysicianHomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    logoutRepository: LogoutRepository,
    onLogoutButtonPressed: (Boolean) -> Unit,
    onAccountInfoButtonPressed: () -> Unit,
    api: DiaVantageApi = DiaVantageApi()
){
    ScreenScaffoldTemplate (
        topBar = {
            CreateTopAppBar(
                title = "Physician Home Page",
                appBarType = TopAppBarTypes.CenterAlignedTopAppBar,
                actions = { PhysicianActions(
                    homeViewModel,
                    logoutRepository,
                    onLogoutButtonPressed,
                    api,
                    modifier) },
                navigationIcon = { PhysicianNavigationIcon(onAccountInfoButtonPressed, modifier) },
                modifier = modifier,
            )
        },
        modifier = modifier,
        content = { PhysicianContent(
            homeViewModel,
            logoutRepository,
            onLogoutButtonPressed,
            modifier
        )}
    )
}


@Composable
fun PhysicianContent(
    homeViewModel: HomeViewModel,
    logoutRepository: LogoutRepository,
    onLogoutButtonPressed: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)

    ){
        Text(
            text = "Physicians` accounts are not currently serviced",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
        Button(
            onClick =  { onLogoutButtonPressed(homeViewModel.logoutUser(logoutRepository)) },
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}


@Composable
fun PhysicianNavigationIcon(
    onAccountInfoButtonPressed: () -> Unit,
    modifier: Modifier
){
    IconButton(
        onClick = onAccountInfoButtonPressed,
        modifier = modifier
    ){
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Localized description",
            modifier = modifier
        )
    }
}


@Composable
fun PhysicianActions(
    homeViewModel: HomeViewModel,
    logoutRepository: LogoutRepository,
    onLogoutButtonPressed: (Boolean) -> Unit,
    api: DiaVantageApi,
    modifier: Modifier,
){
    AccountIcon(
        homeViewModel = homeViewModel,
        logoutRepository = logoutRepository,
        api = api,
        onLogoutButtonPressed = onLogoutButtonPressed,
        modifier = modifier
    )
}


@Composable
@Preview(showBackground = true)
fun PhysicianScreenPreview(){
    DiaVantageMobileTheme {
        PhysicianHomeScreen(
            onLogoutButtonPressed = {},
            onAccountInfoButtonPressed = {},
            logoutRepository = ID.remoteRepository.logoutRepository()
        )
    }
}


