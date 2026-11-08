package com.tecsup.rickymorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tecsup.rickymorty.ui.navigation.AppNav
import com.tecsup.rickymorty.ui.theme.RickMortyCatalogoTheme

class MainActivity : ComponentActivity() {
    private val container get() = (application as CatalogoApplication).container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyCatalogoTheme {
                Scaffold { padding ->
                    AppNav(container.factory, Modifier.padding(padding))
                }
            }
        }
    }
}
