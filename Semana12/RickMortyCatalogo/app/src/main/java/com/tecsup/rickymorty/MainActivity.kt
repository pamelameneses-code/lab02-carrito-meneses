package com.tecsup.rickymorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.lista.ListaScreen
import com.tecsup.rickymorty.ui.theme.RickMortyCatalogoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyCatalogoTheme {
                Scaffold { padding ->
                    ListaScreen(
                        UiState.Exito(listOf(Item(1, "Rick Sanchez", "Humano"), Item(2, "Morty Smith", "Humano"))),
                        reintentar = {},
                        abrir = {},
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}
