package com.example.diavantagemobile.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.R
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
){
    Scaffold(
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "DiaVantageMobile",
                )
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
            LoginLayout(modifier)
        }
    }
}




@Composable
private fun LoginLayout(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(20.dp)
    ) {
        IconImage(modifier = modifier)
        LoginInput(modifier = modifier)
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
private fun LoginButtons(modifier: Modifier= Modifier){
    Row (modifier) {
        ElevatedButton(
            onClick = {}
        ){
            Text(text = "Register")
        }
        Button(
            onClick = { /*TODO*/ }) {
            Text(
                text = "Login"
            )
        }
    }
}



@Composable
private fun LoginInput(modifier: Modifier = Modifier){
    val username = "Username"
    val password = "Password"
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = username,
            onValueChange = {} )
        TextField(
            value = password,
            onValueChange = {})
        LoginButtons(modifier
            .padding(top = 30.dp))
    }

}


@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    DiaVantageMobileTheme {
        LoginScreen()
    }
}