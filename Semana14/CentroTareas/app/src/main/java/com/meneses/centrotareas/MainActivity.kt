package com.meneses.centrotareas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.meneses.centrotareas.viewmodel.TareasViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meneses.centrotareas.ui.PantallaTareas
import com.meneses.centrotareas.ui.theme.CentroTareasTheme
import com.meneses.centrotareas.viewmodel.TareasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentroTareasTheme {
                Scaffold { padding ->
                    PantallaTareas(viewModel(factory = TareasViewModelFactory), Modifier.padding(padding))
                }
            }
        }
    }
}
