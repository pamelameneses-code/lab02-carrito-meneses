package com.tecsup.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavegacion()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var destinoSeleccionado by remember { mutableStateOf("Inicio") }
    var contadorFavoritos by remember { mutableIntStateOf(0) }

    val listaProductos = listOf(
        Producto(id = 1, nombre = "Audifonos", precio = 89.00),
        Producto(id = 2, nombre = "Smartwatch", precio = 199.00),
        Producto(id = 3, nombre = "Funda celular", precio = 25.00)
    )

    // Color morado igual al diseño de la guía
    val moradoHeader = Color(0xFF5E2180)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                destinoSeleccionado = destinoSeleccionado,
                contadorFavoritos = contadorFavoritos,
                onNavegar = { destino: String ->
                    destinoSeleccionado = destino
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "TECSUP Store",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )
                            if (destinoSeleccionado == "Inicio") {
                                Text(
                                    text = "Mas vendidos",
                                    color = Color.White.copy(alpha = 0.8f),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = moradoHeader
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (destinoSeleccionado) {
                    "Inicio" -> PantallaInicio(
                        listaProductos = listaProductos,
                        onAgregarFavorito = { contadorFavoritos++ }
                    )
                    "Mis pedidos" -> PantallaGenerica(
                        titulo = "Mis Pedidos",
                        mensaje = "Aquí verás la lista de tus pedidos realizados."
                    )
                    "Favoritos" -> PantallaGenerica(
                        titulo = "Mis Favoritos",
                        mensaje = "Tienes $contadorFavoritos producto(s) agregados a favoritos."
                    )
                    "Perfil" -> PantallaGenerica(
                        titulo = "Perfil del Usuario",
                        mensaje = "Nombre: Pamela Meneses\nCorreo: pamela.meneses@tecsup.edu.pe"
                    )
                    "Cerrar sesion" -> PantallaGenerica(
                        titulo = "Sesión Cerrada",
                        mensaje = "Has cerrado sesión correctamente."
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(
    listaProductos: List<Producto>,
    onAgregarFavorito: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(listaProductos) { prod ->
                TarjetaProducto(
                    producto = prod,
                    onAgregarFavorito = onAgregarFavorito
                )
            }
        }
    }
}

@Composable
fun PantallaGenerica(titulo: String, mensaje: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = titulo, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = mensaje, style = MaterialTheme.typography.bodyLarge)
    }
}