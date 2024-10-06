package com.example.diavantagemobile.ui.registration

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.diavantagemobile.R
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.DiaVantageApi


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RegistrationWebView(
    modifier: Modifier = Modifier,
    api: DiaVantageApi = DiaVantageApi(),
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = MyWebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true;
                settings.displayZoomControls = false;
            }
        },
        update = { webView ->
            webView.loadUrl(api.apiStrings.hostName + api.apiStrings.patientRegister)
        },
        modifier = modifier
            .fillMaxSize()
    )
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    api: DiaVantageApi = DiaVantageApi(),
){
    ScreenScaffoldTemplate(
        modifier = modifier,
        title = "Registration Page",
        content = {
            RegistrationWebView(
                modifier = modifier,
                api = api,
            )
        }
    )
}


@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview(){
    DiaVantageMobileTheme {
        RegistrationScreen()
    }
}