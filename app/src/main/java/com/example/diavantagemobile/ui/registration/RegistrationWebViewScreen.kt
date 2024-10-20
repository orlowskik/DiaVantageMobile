package com.example.diavantagemobile.ui.registration

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.data.ApiStrings


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RegistrationWebView(
    modifier: Modifier = Modifier,
    apiStrings: ApiStrings = ApiStrings(),
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = MyWebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
            }
        },
        update = { webView ->
            webView.loadUrl(apiStrings.patientRegister)
        },
        modifier = modifier
            .fillMaxSize()
    )
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    returnToLogin: () -> Unit = {},
){
    ScreenScaffoldTemplate(
        modifier = modifier,
        title = "Registration Page",
        content = {
            RegistrationWebView(
                modifier = modifier,
            )
        },
        navigationIcon = {
            IconButton(onClick = { returnToLogin() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview(){
    DiaVantageMobileTheme {
        RegistrationScreen(
        )
    }
}