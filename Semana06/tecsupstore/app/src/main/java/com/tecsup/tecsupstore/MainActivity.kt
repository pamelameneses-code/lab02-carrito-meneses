package com.tecsup.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.ui.theme.TecsupstoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TecsupstoreTheme {
                PantallaPrincipal()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var destinoActual by remember { mutableStateOf("inicio") }

    val listaProductos = remember {
        listOf(
            Producto(1, "Audífonos Bluetooth", 89.00),
            Producto(2, "Smartwatch Deportivo", 199.00),
            Producto(3, "Funda de Gel", 25.00)
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                destinoActual = destinoActual,
                onNavegar = { nuevaRuta ->
                    destinoActual = nuevaRuta
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (destinoActual) {
                    "inicio" -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                Text(
                                    text = "Productos Destacados",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            items(listaProductos) { producto ->
                                TarjetaProducto(
                                    producto = producto,
                                    onAgregarFavorito = { }
                                )
                            }
                        }
                    }
                    "pedidos" -> VistaMensaje("Pantalla: Mis Pedidos")
                    "favoritos" -> VistaMensaje("Pantalla: Favoritos")
                    "perfil" -> VistaMensaje("Pantalla: Perfil de Usuario")
                }
            }
        }
    }
}

@Composable
fun VistaMensaje(texto: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = texto, style = MaterialTheme.typography.titleMedium)
    }
}