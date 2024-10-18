package com.example.diavantagemobile.ui.registration

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.diavantagemobile.util.api.DiaVantageApi

class MyWebViewClient : WebViewClient() {
    private val api = DiaVantageApi()


    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        Log.i("WebViewClient", "shouldOverrideUrlLoading: $url")
        return url != api.apiStrings.patientRegister
    }
}