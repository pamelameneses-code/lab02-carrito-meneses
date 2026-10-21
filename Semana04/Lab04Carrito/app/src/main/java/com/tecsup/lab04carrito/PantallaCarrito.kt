package com.tecsup.lab04carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    val productos = remember { mutableStateListOf<Producto>() }

    // Estado para controlar el AlertDialog de confirmación de borrado
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    // Cálculos dinámicos con Descuento (Reto 2)
    val subtotalBase = productos.sumOf { it.precio * it.cantidad }

    // Descuento según reglas del Lab 02 usando `when`
    val porcentajeDescuento = when {
        subtotalBase > 5000 -> 0.10
        subtotalBase > 3000 -> 0.05
        else -> 0.0
    }
    val montoDescuento = subtotalBase * porcentajeDescuento
    val subtotalConDescuento = subtotalBase - montoDescuento
    val igv = subtotalConDescuento * 0.18
    val total = subtotalConDescuento + igv

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito TECSUP", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Formulario
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio (S/)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val precioNum = precio.toDoubleOrNull() ?: 0.0
                    val cantidadNum = cantidad.toIntOrNull() ?: 0
                    if (nombre.isNotBlank() && precioNum > 0 && cantidadNum > 0) {
                        productos.add(Producto(nombre, precioNum, cantidadNum))
                        nombre = ""
                        precio = ""
                        cantidad = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("AGREGAR")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista o Estado Vacío
            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "El carrito está vacío",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(productos) { producto ->
                        TarjetaProducto(
                            producto = producto,
                            onEliminar = { productoAEliminar = producto }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Panel de Totales
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:")
                        Text("S/ ${String.format("%.2f", subtotalBase)}")
                    }

                    // Se muestra solo si aplica descuento (Reto 2)
                    if (montoDescuento > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Descuento (${(porcentajeDescuento * 100).toInt()}%):",
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = "- S/ ${String.format("%.2f", montoDescuento)}",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("IGV (18%):")
                        Text("S/ ${String.format("%.2f", igv)}")
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontWeight = FontWeight.Bold)
                        Text(
                            "S/ ${String.format("%.2f", total)}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }

    // Reto 1: AlertDialog de Confirmación de Borrado
    productoAEliminar?.let { prod ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = { Text("¿Eliminar este producto?") },
            text = { Text("¿Estás seguro de que deseas eliminar \"${prod.nombre}\" del carrito?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        productos.remove(prod)
                        productoAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { productoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TarjetaProducto(producto: Producto, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "S/ ${producto.precio} x ${producto.cantidad} = S/ ${String.format("%.2f", producto.precio * producto.cantidad)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TextButton(onClick = onEliminar) {
                Text("Eliminar", color = Color.Red)
            }
        }
    }
}