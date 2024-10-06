package com.example.diavantagemobile.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.R
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogoutButtonPressed: (Boolean) -> Unit,
    api: DiaVantageApi = DiaVantageApi()
){
    ScreenScaffoldTemplate (
        topBar = {
            CustomTopAppBar(
                onLogoutButtonPressed = onLogoutButtonPressed,
                api = api,
                modifier = modifier
            )
        },
        modifier = modifier,
        content = {
            Text("Home Page")
            Text("You have been logged")
        }
    )
}

@Composable
fun AccountIcon(
    api: DiaVantageApi,
    onLogoutButtonPressed: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            Icons.Filled.AccountBox,
            contentDescription = "Account Box",
            modifier = modifier.fillMaxSize()
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Logout") },
            onClick = { runBlocking { onLogoutButtonPressed(api.logout()) } },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    onLogoutButtonPressed: (Boolean) -> Unit,
    api: DiaVantageApi,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        colors = centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Home Page")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",

                    )
            }
        },
        actions = { AccountIcon(
            api = api,
            onLogoutButtonPressed = onLogoutButtonPressed,
            modifier = modifier) }

    )
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    DiaVantageMobileTheme {
        HomeScreen(onLogoutButtonPressed = {})
    }
}