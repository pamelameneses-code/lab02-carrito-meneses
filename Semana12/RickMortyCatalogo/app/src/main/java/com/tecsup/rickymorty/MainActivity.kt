package com.tecsup.rickymorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.rickymorty.ui.lista.ListaViewModel
import com.tecsup.rickymorty.ui.lista.ListaScreen
import com.tecsup.rickymorty.ui.theme.RickMortyCatalogoTheme

class MainActivity : ComponentActivity() {
    private val container by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyCatalogoTheme {
                Scaffold { padding ->
                    val vm: ListaViewModel = viewModel(factory = container.factory)
                    ListaScreen(
                        vm.estado.collectAsStateWithLifecycle().value,
                        reintentar = vm::refrescar,
                        abrir = {},
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}
