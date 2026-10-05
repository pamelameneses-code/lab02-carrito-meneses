package com.tecsup.mibodega.ui.cliente.screens.categorias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BarraInferior

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(onNavegarTab: (Int) -> Unit) {
    val categorias = listaCategorias.filter { it != "Todos" }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías", fontWeight = FontWeight.Bold) }) },
        bottomBar = { BarraInferior(seleccionado = 1, onNavegar = onNavegarTab) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(categorias) { categoria ->
                val cantidad = listaProductosFake.count { it.categoria == categoria }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(categoria, style = MaterialTheme.typography.titleMedium)
                        Text("$cantidad productos")
                    }
                }
            }
        }
    }
}