package com.tecsup.mibodega.ui.cliente.screens.pedidos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tecsup.mibodega.ui.componentes.BarraInferior

@Composable
fun PedidosScreen(onNavegarTab: (Int) -> Unit) {
    Scaffold(
        bottomBar = { BarraInferior(seleccionado = 2, onNavegar = onNavegarTab) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Aún no tienes pedidos")
        }
    }
}