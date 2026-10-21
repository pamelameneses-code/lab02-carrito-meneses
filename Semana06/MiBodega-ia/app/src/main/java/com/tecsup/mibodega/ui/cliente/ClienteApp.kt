package com.tecsup.mibodega.ui.cliente

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen

private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val INICIO = "inicio"
    const val FAVORITOS = "favoritos"
    const val PEDIDOS = "pedidos"
    const val PERFIL = "perfil"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp(
    modoOscuro: Boolean = false,
    onCambiarModoOscuro: (Boolean) -> Unit = {}
) {
    val navController = rememberNavController()

    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var favoritos by remember { mutableStateOf<List<Int>>(emptyList()) }
    var nombreUsuario by remember { mutableStateOf("Cliente") }
    var telefonoUsuario by remember { mutableStateOf("-") }
    var numeroPedido by remember { mutableStateOf(1000) }
    var direccionPedido by remember { mutableStateOf("") }
    var metodoPagoPedido by remember { mutableStateOf("") }

    val alternarFavorito: (Producto) -> Unit = { producto ->
        favoritos = if (producto.id in favoritos) favoritos - producto.id else favoritos + producto.id
    }

    // Navegación de la barra inferior: 0 Inicio, 1 Favoritos, 2 Pedidos, 3 Perfil
    val navegarTab: (Int) -> Unit = { indice ->
        val destino = when (indice) {
            0 -> Rutas.INICIO
            1 -> Rutas.FAVORITOS
            2 -> Rutas.PEDIDOS
            else -> Rutas.PERFIL
        }
        navController.navigate(destino) {
            popUpTo(Rutas.INICIO)
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA,
        enterTransition = { slideInHorizontally { it / 3 } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it / 3 } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it / 3 } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it / 3 } + fadeOut() }
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = { navController.navigate(Rutas.LOGIN) },
                onTerminos = { /* TODO: abrir términos y condiciones */ }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onVolver = { navController.popBackStack() },
                onIngresar = { usuario ->
                    nombreUsuario = usuario
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    nombreUsuario = nombre
                    telefonoUsuario = telefono
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                favoritos = favoritos,
                onToggleFavorito = alternarFavorito,
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onNavegarTab = navegarTab
            )
        }

        composable(Rutas.FAVORITOS) {
            FavoritosScreen(
                productos = listaProductosFake.filter { it.id in favoritos },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onQuitarFavorito = alternarFavorito,
                onNavegarTab = navegarTab
            )
        }

        composable(Rutas.PEDIDOS) {
            PedidosScreen(
                pedidos = pedidos,
                onNavegarTab = navegarTab
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                nombre = nombreUsuario,
                telefono = telefonoUsuario,
                modoOscuro = modoOscuro,
                onCambiarModoOscuro = onCambiarModoOscuro,
                onNavegarTab = navegarTab,
                onCerrarSesion = {
                    carrito = emptyList()
                    navController.navigate(Rutas.BIENVENIDA) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = { navController.navigate(Rutas.ENTREGA) }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onConfirmarPedido = { direccion, metodo ->
                    numeroPedido += 1
                    direccionPedido = direccion
                    metodoPagoPedido = metodo

                    // Guardar el pedido (antes de vaciar el carrito)
                    pedidos = listOf(
                        Pedido(
                            numero = numeroPedido,
                            cantidadProductos = carrito.sumOf { it.cantidad },
                            total = carrito.sumOf { it.producto.precio * it.cantidad } + 4.0,
                            direccion = direccion,
                            metodoPago = metodo
                        )
                    ) + pedidos

                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.INICIO) { inclusive = false }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                carrito = carrito,
                numeroPedido = numeroPedido,
                direccion = direccionPedido,
                metodoPago = metodoPagoPedido,
                onVerEstado = {
                    carrito = emptyList()
                    navegarTab(2)
                },
                onVolverAlInicio = {
                    carrito = emptyList()
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                    }
                }
            )
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}