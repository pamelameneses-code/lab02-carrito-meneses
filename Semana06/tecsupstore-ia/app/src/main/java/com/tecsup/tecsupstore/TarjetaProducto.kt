package com.tecsup.tecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun TarjetaProducto(
    producto: Producto,
    onAgregarFavorito: () -> Unit
) {
    var menuExpandido by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Ícono de bolsa de compras a la izquierda
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(2.dp))
                    // Muestra 2 decimales siempre (ejemplo: S/ 89.00)
                    Text(
                        text = String.format(Locale.US, "S/ %.2f", producto.precio),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Menú contextual desplegable (3 puntos)
            Box {
                IconButton(onClick = { menuExpandido = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones del producto"
                    )
                }

                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Favoritos") },
                        leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        onClick = {
                            onAgregarFavorito()
                            menuExpandido = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        leadingIcon = { Icon(Icons.Default.Share, contentDescription = null) },
                        onClick = { menuExpandido = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Reportar") },
                        leadingIcon = { Icon(Icons.Default.Warning, contentDescription = null) },
                        onClick = { menuExpandido = false }
                    )
                }
            }
        }
    }
}