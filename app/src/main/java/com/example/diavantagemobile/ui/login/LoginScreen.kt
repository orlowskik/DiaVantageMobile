package com.example.diavantagemobile.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.R
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ActivityScenario.launch
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    api: DiaVantageApi = DiaVantageApi(),
){
    val loginUiState by loginViewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("DiaVantage | Login Page")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row (
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                ){
                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Center,
                        text = "DiaVantageMobile",
                    )
                    Image(
                        painter = painterResource(R.drawable.tm),
                        contentDescription = null,
                        modifier = modifier
                            .size(25.dp)
                            .padding(bottom = 10.dp)
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LoginLayout(
                inputUsername = loginViewModel.inputUsername,
                inputPassword = loginViewModel.inputPassword,
                onUsernameFilled = { loginViewModel.updateUsername(it) },
                onPasswordFilled = { loginViewModel.updatePassword(it) },
                onLoginButtonPressed = {loginViewModel.authenticateUser(api = api)},
                modifier = modifier
            )
        }
    }
}




@Composable
private fun LoginLayout(
    inputUsername: String,
    inputPassword: String,
    onPasswordFilled: (String) -> Unit,
    onUsernameFilled: (String) -> Unit,
    onLoginButtonPressed: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconImage(modifier = modifier)
        LoginInput(
            inputUsername = inputUsername,
            inputPassword = inputPassword,
            onUsernameFilled = onUsernameFilled,
            onPasswordFilled = onPasswordFilled,
            onLoginButtonPressed = onLoginButtonPressed,
            modifier = modifier,

        )
    }
}


@Composable
private fun IconImage(modifier: Modifier = Modifier){
    val image = painterResource(id = R.drawable.diaveb_icon)
    val borderWidth = 3.dp
    val imageSize = 200.dp
    val roundRatio = 40.dp
    val iconBorderColor = Color(0xff118f92)
    Box(
        modifier = modifier){
        Image(
           painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alpha = 1.0F,
            modifier = modifier
                .padding(bottom = 20.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(roundRatio))
                .border(
                    BorderStroke(borderWidth, iconBorderColor),
                    RoundedCornerShape(roundRatio)
                )


        )
    }
}


@Composable
private fun LoginButtons(
    onLoginButtonPressed: () -> Unit,
    modifier: Modifier = Modifier){
    Column (modifier = modifier
        .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Button(
            modifier = modifier
                .size(width= 250.dp, height = 50.dp),
            onClick = onLoginButtonPressed ) {
            Text(
                text = stringResource(R.string.login)
            )
        }
        Text(
            modifier = modifier,
            text = "or"
        )
        ElevatedButton(
            modifier = modifier
                .size(width = 250.dp, height = 50.dp),
            onClick = {}
        ){
            Text(text = stringResource(R.string.register))
        }

    }
}



@Composable
private fun LoginInput(
    inputUsername: String,
    inputPassword: String,
    onUsernameFilled: (String) -> Unit,
    onPasswordFilled: (String) -> Unit,
    onLoginButtonPressed: () -> Unit,
    modifier: Modifier = Modifier){
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = inputUsername,
            onValueChange = onUsernameFilled,
            label = { Text("Username") },
            singleLine = true,
            placeholder = { Text("Username") }
        )
        TextField(
            value = inputPassword,
            onValueChange = onPasswordFilled,
            label = { Text("Password") },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(
                    onClick = {passwordVisible = !passwordVisible}
                ) {
                    Icon(imageVector =  image, description)
                }
            }
        )
        LoginButtons(
            onLoginButtonPressed = onLoginButtonPressed,
            modifier = modifier)
    }

}


@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    DiaVantageMobileTheme {
        LoginScreen()
    }
}