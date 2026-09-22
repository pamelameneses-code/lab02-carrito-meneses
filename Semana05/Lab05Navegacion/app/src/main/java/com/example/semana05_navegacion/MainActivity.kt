package com.example.semana05_navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.semana05_navegacion.navigation.AppNavigation
import com.example.semana05_navegacion.ui.theme.Semana05_NavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Semana05_NavegacionTheme {
                AppNavigation()
            }
        }
    }
}
