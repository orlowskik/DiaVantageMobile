package com.example.diavantagemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            DiaVantageMobileTheme {
                    DiaVantageApp()
            }
        }
    }
}