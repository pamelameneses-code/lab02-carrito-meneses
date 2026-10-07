package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BarraInferior
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

private enum class OrdenPrecio { NINGUNO, MENOR_A_MAYOR, MAYOR_A_MENOR }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    productos: List<Producto> = listaProductosFake,
    cantidadCarrito: Int,
    favoritos: List<Int> = emptyList(),
    onToggleFavorito: (Producto) -> Unit = {},
    onVerCarrito: () -> Unit,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onNavegarTab: (Int) -> Unit = {}
) {
    var categoriaSeleccionada by remember { mutableStateOf(listaCategorias.first()) }
    var textoBusqueda by remember { mutableStateOf("") }
    var orden by remember { mutableStateOf(OrdenPrecio.NINGUNO) }
    var menuOrdenAbierto by remember { mutableStateOf(false) }

    val productosFiltrados = productos.filter { producto ->
        val coincideCategoria = categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada
        val coincideBusqueda = producto.nombre.contains(textoBusqueda, ignoreCase = true)
        coincideCategoria && coincideBusqueda
    }

    val productosOrdenados = when (orden) {
        OrdenPrecio.MENOR_A_MAYOR -> productosFiltrados.sortedBy { it.precio }
        OrdenPrecio.MAYOR_A_MENOR -> productosFiltrados.sortedByDescending { it.precio }
        OrdenPrecio.NINGUNO -> productosFiltrados
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Bodega", fontWeight = FontWeight.Bold) },
                actions = {
                    Box {
                        IconButton(onClick = { menuOrdenAbierto = true }) {
                            Icon(Icons.Default.SwapVert, contentDescription = "Ordenar por precio")
                        }
                        DropdownMenu(
                            expanded = menuOrdenAbierto,
                            onDismissRequest = { menuOrdenAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Precio: menor a mayor") },
                                onClick = { orden = OrdenPrecio.MENOR_A_MAYOR; menuOrdenAbierto = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Precio: mayor a menor") },
                                onClick = { orden = OrdenPrecio.MAYOR_A_MENOR; menuOrdenAbierto = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Sin orden") },
                                onClick = { orden = OrdenPrecio.NINGUNO; menuOrdenAbierto = false }
                            )
                        }
                    }
                    IconButton(onClick = onVerCarrito) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge { Text("$cantidadCarrito") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        },
        bottomBar = { BarraInferior(seleccionado = 0, onNavegar = onNavegarTab) }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = VerdeBodega
                )
            )

            Text(
                text = "Productos destacados",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 20.dp, bottom = 4.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(listaCategorias) { categoria ->
                    ChipCategoria(
                        texto = categoria,
                        seleccionado = categoria == categoriaSeleccionada,
                        onClick = { categoriaSeleccionada = categoria }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(productosOrdenados) { producto ->
                    val esFavorito = producto.id in favoritos
                    Box {
                        ProductoCard(
                            producto = producto,
                            onClick = { onProductoClick(producto) },
                            onAgregar = { onAgregarProducto(producto) }
                        )
                        IconButton(
                            onClick = { onToggleFavorito(producto) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (esFavorito) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.surfaceVariant
    val contenido = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .background(fondo, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(text = texto, color = contenido, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InicioPreview() {
    BodegaTheme {
        InicioScreen(
            cantidadCarrito = 3,
            onVerCarrito = {},
            onProductoClick = {},
            onAgregarProducto = {}
        )
    }
}