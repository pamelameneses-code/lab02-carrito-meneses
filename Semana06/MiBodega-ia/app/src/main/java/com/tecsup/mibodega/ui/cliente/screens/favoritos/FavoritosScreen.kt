package com.tecsup.mibodega.ui.cliente.screens.favoritos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.componentes.BarraInferior
import com.tecsup.mibodega.ui.componentes.ProductoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    productos: List<Producto>,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onQuitarFavorito: (Producto) -> Unit,
    onNavegarTab: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis favoritos", fontWeight = FontWeight.Bold) }) },
        bottomBar = { BarraInferior(seleccionado = 1, onNavegar = onNavegarTab) }
    ) { padding ->
        if (productos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Aún no tienes favoritos", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Toca el corazón de un producto para guardarlo",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                items(productos) { producto ->
                    Box {
                        ProductoCard(
                            producto = producto,
                            onClick = { onProductoClick(producto) },
                            onAgregar = { onAgregarProducto(producto) }
                        )
                        IconButton(
                            onClick = { onQuitarFavorito(producto) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Quitar de favoritos",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}