package com.tecsup.cafecampus.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import com.tecsup.cafecampus.CafeApp
import com.tecsup.cafecampus.model.Rol
import com.tecsup.cafecampus.viewmodel.*
import com.tecsup.cafecampus.ui.cliente.*
import com.tecsup.cafecampus.ui.admin.*
import com.tecsup.cafecampus.ui.components.*
import com.tecsup.cafecampus.ui.theme.CafeTheme

private data class Destino(val ruta: String, val titulo: String, val icono: ImageVector)

// Punto de composición: solo aquí se resuelven las dependencias de los ViewModels.
@Composable fun NavCafe(app: CafeApp) {
    val factory = remember(app) { CafeViewModelFactory(app) }
    val sesion: SesionViewModel = viewModel(factory=factory)
    val menu: MenuViewModel = viewModel(factory=factory)
    val usuario by sesion.usuarioActual.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val cargando by sesion.cargando.collectAsState()
    var inicializado by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(cargando) { if(!cargando) inicializado=true }
    val nav = rememberNavController()
    CafeTheme(if(usuario?.rol==Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE,oscuro) {
        Surface(Modifier.fillMaxSize(),color=MaterialTheme.colorScheme.background) {
            if(!inicializado) {
                Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center) { CircularProgressIndicator() }
            } else {
                val entrada by nav.currentBackStackEntryAsState()
                val ruta = entrada?.destination?.route
                val admin = usuario?.rol==Rol.ADMIN.name
                val destinos = if(admin) listOf(Destino(Rutas.PANEL,"Panel",Icons.Default.Dashboard),Destino(Rutas.PEDIDOS_ADMIN,"Pedidos",Icons.Default.ReceiptLong),Destino(Rutas.MENU_ADMIN,"Menú",Icons.Default.RestaurantMenu),Destino(Rutas.AJUSTES,"Ajustes",Icons.Default.Settings))
                    else listOf(Destino(Rutas.MENU,"Menú",Icons.Default.RestaurantMenu),Destino(Rutas.MIS_PEDIDOS,"Pedidos",Icons.Default.ReceiptLong),Destino(Rutas.PERFIL,"Perfil",Icons.Default.Person))
                val principal = destinos.any { it.ruta==ruta }
                val mostrarBarra = principal || ruta==Rutas.SEGUIMIENTO
                val titulo = when(ruta) {
                    Rutas.PRODUCTO -> "Detalle del producto"; Rutas.CONFIRMAR -> "Confirmar pedido"
                    Rutas.SEGUIMIENTO -> "Seguimiento"; Rutas.DETALLE_ADMIN -> "Detalle del pedido"
                    Rutas.FORMULARIO -> if(entrada?.arguments?.getInt("id",0)==0) "Nuevo producto" else "Editar producto"
                    Rutas.PEDIDOS_ADMIN -> "Pedidos"; Rutas.MENU_ADMIN -> "Gestión del menú"; Rutas.AJUSTES -> "Ajustes"
                    Rutas.MIS_PEDIDOS -> "Mis pedidos"
                    Rutas.PERFIL -> "Mi perfil"; else -> "CafeCampus"
                }
                val clienteFactory = remember(app,usuario?.id) { CafeViewModelFactory(app,usuario?.id ?: 0) }
                val pedidosCliente: PedidosClienteViewModel = viewModel(key="pedidos-${usuario?.id ?: 0}",factory=clienteFactory)
                val panel: PanelAdminViewModel = viewModel(factory=factory)
                val pedidosAdmin: PedidosAdminViewModel = viewModel(factory=factory)
                val menuAdmin: MenuAdminViewModel = viewModel(factory=factory)
                LaunchedEffect(usuario?.id) {
                    val actual = nav.currentDestination?.route
                    if(usuario!=null) {
                        val grafo = if(admin) Rutas.ADMIN else Rutas.CLIENTE
                        val dentro = nav.currentDestination?.hierarchy?.any { it.route==grafo } == true
                        if(!dentro) nav.navigate(grafo) { popUpTo(nav.graph.id) { inclusive=false }; launchSingleTop=true }
                    } else if(actual!=null && actual !in listOf(Rutas.SPLASH,Rutas.LOGIN)) {
                        menu.vaciar()
                        nav.navigate(Rutas.LOGIN) { popUpTo(nav.graph.id) { inclusive=false }; launchSingleTop=true }
                    }
                }
                Scaffold(
                    containerColor=MaterialTheme.colorScheme.background,
                    topBar={
                        if(usuario!=null && ruta !in listOf(Rutas.SPLASH,Rutas.LOGIN)) {
                            if(admin) {
                                if(ruta!=Rutas.PANEL) CabeceraAdmin(titulo) {
                                    if(!nav.popBackStack()) nav.navigate(Rutas.PANEL) {launchSingleTop=true}
                                }
                            }
                            else if(ruta!=Rutas.MENU) CabeceraEstudiante(titulo) {
                                if(!nav.popBackStack()) nav.navigate(Rutas.MENU) {launchSingleTop=true}
                            }
                        }
                    },
                    bottomBar={
                        if(usuario!=null && mostrarBarra) {
                            if(admin) NavegacionAdmin(
                                seleccionado=destinos.indexOfFirst {it.ruta==ruta}.coerceAtLeast(0),
                                onDestino={i -> nav.navigate(destinos[i].ruta) {
                                    popUpTo(Rutas.PANEL) {saveState=true}; launchSingleTop=true; restoreState=true
                                }}
                            ) else NavegacionEstudiante(
                                seleccionado=when(ruta) {Rutas.MIS_PEDIDOS,Rutas.SEGUIMIENTO -> 1; Rutas.PERFIL -> 2; else -> 0},
                                onDestino={i ->
                                    nav.navigate(destinos[i].ruta) { popUpTo(Rutas.MENU) {saveState=true}; launchSingleTop=true; restoreState=true }
                                }
                            )
                        }
                    }
                ) { padding ->
                    NavHost(navController=nav,startDestination=Rutas.LOGIN,modifier=Modifier.padding(padding)) {
                        composable(Rutas.SPLASH) { SplashScreen({nav.navigate(Rutas.LOGIN)},{nav.navigate(Rutas.LOGIN)}) }
                        composable(Rutas.LOGIN) { LoginScreen(sesion) }
                        navigation(startDestination=Rutas.MENU,route=Rutas.CLIENTE) {
                            composable(Rutas.MENU) { MenuScreen(menu,sesion,{nav.navigate(Rutas.producto(it))},{nav.navigate(Rutas.CONFIRMAR)}) }
                            composable(Rutas.MIS_PEDIDOS) { MisPedidosScreen(pedidosCliente,menu,
                                onPedido={nav.navigate(Rutas.seguimiento(it))},
                                onRepetido={nav.navigate(Rutas.CONFIRMAR) {launchSingleTop=true}}) }
                            composable(Rutas.PERFIL) { AjustesScreen(sesion,sesion::cambiarPerfil,sesion::cerrarSesion) }
                            composable(Rutas.PRODUCTO,arguments=listOf(navArgument("id"){type=NavType.IntType})) { entry ->
                                DetalleProductoScreen(menu,entry.arguments!!.getInt("id")) {nav.popBackStack()}
                            }
                            composable(Rutas.CONFIRMAR) { ConfirmarPedidoScreen(pedidosCliente,menu,{id ->
                                nav.navigate(Rutas.seguimiento(id)) {popUpTo(Rutas.CONFIRMAR){inclusive=true}}
                            },{nav.popBackStack(Rutas.MENU,false)}) }
                            composable(Rutas.SEGUIMIENTO,arguments=listOf(navArgument("id"){type=NavType.IntType})) { entry -> SeguimientoScreen(pedidosCliente,entry.arguments!!.getInt("id")) }
                        }
                        navigation(startDestination=Rutas.PANEL,route=Rutas.ADMIN) {
                            composable(Rutas.PANEL) { PanelScreen(panel,onPedido={nav.navigate(Rutas.detalleAdmin(it))},onVerTodos={
                                pedidosAdmin.estadoSeleccionado.value=com.tecsup.cafecampus.model.EstadoPedido.RECIBIDO
                                nav.navigate(Rutas.PEDIDOS_ADMIN) {launchSingleTop=true}
                            }) }
                            composable(Rutas.PEDIDOS_ADMIN) { PedidosAdminScreen(pedidosAdmin) {nav.navigate(Rutas.detalleAdmin(it))} }
                            composable(Rutas.DETALLE_ADMIN,arguments=listOf(navArgument("id"){type=NavType.IntType})) { entry -> DetallePedidoAdminScreen(pedidosAdmin,entry.arguments!!.getInt("id")) }
                            composable(Rutas.MENU_ADMIN) { MenuAdminScreen(menuAdmin) {id -> menuAdmin.consumirGuardado(); nav.navigate(Rutas.formulario(id))} }
                            composable(Rutas.FORMULARIO,arguments=listOf(navArgument("id"){type=NavType.IntType; defaultValue=0})) { entry ->
                                FormularioProductoScreen(menuAdmin,entry.arguments!!.getInt("id").takeIf {it!=0},onGuardado={nav.popBackStack()},onCancelar={nav.popBackStack()})
                            }
                            composable(Rutas.AJUSTES) { AjustesScreen(sesion,sesion::cambiarPerfil,sesion::cerrarSesion) }
                        }
                    }
                }
            }
        }
    }
}
