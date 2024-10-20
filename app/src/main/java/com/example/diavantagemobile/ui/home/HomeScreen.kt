package com.example.diavantagemobile.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardReturn
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.ID
import com.example.diavantagemobile.util.data.TopAppBarTypes
import com.example.diavantagemobile.util.api.login.LogoutRepository
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    onLogoutButtonPressed: (Boolean) -> Unit,
    onAccountInfoButtonPressed: () -> Unit,
    onBloodPress: () -> Unit,
    onGlucosePress: () -> Unit,
    onPhysiciansPress: () -> Unit,
    onHistoryPress: () -> Unit,

    logoutRepository: LogoutRepository,
){
    ScreenScaffoldTemplate (
        topBar = {
            CreateTopAppBar(
                title = "Home Page",
                appBarType = TopAppBarTypes.CenterAlignedTopAppBar,
                actions = { HomeActions(
                    homeViewModel,
                    logoutRepository,
                    onLogoutButtonPressed,
                    modifier) },
                navigationIcon = { HomeNavigationIcon(onAccountInfoButtonPressed, modifier) },
                modifier = modifier,
            )
        },
        modifier = modifier,
        content = { HomeContent(
            onGlucosePress = onGlucosePress,
            onBloodPress = onBloodPress,
            onPhysiciansPress = onPhysiciansPress,
            onHistoryPress = onHistoryPress,
        )}

    )
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onGlucosePress: () -> Unit = {},
    onBloodPress: () -> Unit = {},
    onPhysiciansPress: () -> Unit = {},
    onHistoryPress: () -> Unit = {}
){

    val options = mapOf(
        "Insert glucose" to onGlucosePress,
        "Insert blood pressure" to onBloodPress,
        "Show measurement history" to onHistoryPress,
        "Show physicians" to onPhysiciansPress,
        )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)

    ){
        options.forEach {
            Button(
                onClick = it.value,
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 25.dp)
            ){
                Text(
                    it.key,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
fun AccountIcon(
    homeViewModel: HomeViewModel,
    logoutRepository: LogoutRepository,
    onLogoutButtonPressed: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    val dividerWidth = 115.dp

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            Icons.Filled.AccountBox,
            contentDescription = "Account Box",
            modifier = modifier.fillMaxSize()
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = modifier
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
        ) {
            DropdownMenuItem(
                text = { Text("Account info") },
                onClick = {},
                trailingIcon = {
                    Icon(
                        Icons.Filled.ManageAccounts,
                        contentDescription = "Account info icon",
                        modifier = modifier
                    )
                },
                modifier = modifier
            )
            HorizontalDivider(
                modifier = modifier
                    .width(dividerWidth)
            )
            DropdownMenuItem(
                text = { Text("Logout") },
                onClick = { runBlocking { onLogoutButtonPressed(homeViewModel.logoutUser(logoutRepository)) } },
                trailingIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardReturn,
                        contentDescription = "Logout icon",
                        modifier = modifier
                    )
                },
                modifier = modifier
            )
        }
    }
}


@Composable
fun HomeNavigationIcon(
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
fun HomeActions(
    homeViewModel: HomeViewModel,
    logoutRepository: LogoutRepository,
    onLogoutButtonPressed: (Boolean) -> Unit,
    modifier: Modifier,
){
    AccountIcon(
        homeViewModel = homeViewModel,
        logoutRepository =  logoutRepository,
        onLogoutButtonPressed = onLogoutButtonPressed,
        modifier = modifier
    )
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    DiaVantageMobileTheme {
        HomeScreen(
            onLogoutButtonPressed = {},
            onAccountInfoButtonPressed = {},
            onPhysiciansPress = {},
            onBloodPress = {},
            onGlucosePress = {},
            onHistoryPress = {},
            logoutRepository = ID.remoteRepository.logoutRepository()
        )
    }
}


