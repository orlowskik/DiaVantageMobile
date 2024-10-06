package com.example.diavantagemobile.ui.registration

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.diavantagemobile.util.api.DiaVantageApi

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RegistrationWebViewScreen(
    modifier: Modifier = Modifier,
    api: DiaVantageApi = DiaVantageApi(),
){
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl(api.apiStrings.hostName + api.apiStrings.patientRegister)
        }
    )
}