package com.tecsup.cafecampus.ui.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.tecsup.cafecampus.CafeApp
import com.tecsup.cafecampus.model.Rol
import com.tecsup.cafecampus.viewmodel.*
import com.tecsup.cafecampus.ui.cliente.*
import com.tecsup.cafecampus.ui.components.*
import com.tecsup.cafecampus.ui.theme.CafeTheme

@Composable fun NavCafe(app: CafeApp) {
    val factory = remember(app) {CafeViewModelFactory(app)}
    val sesion: SesionViewModel = viewModel(factory=factory)
    val menu: MenuViewModel = viewModel(factory=factory)
    val usuario by sesion.usuarioActual.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val cargando by sesion.cargando.collectAsState()
    var listo by remember {mutableStateOf(false)}
    LaunchedEffect(cargando) {if(!cargando) listo=true}
    val nav = rememberNavController()
    val admin = usuario?.rol==Rol.ADMIN.name
    CafeTheme(if(admin) Rol.ADMIN else Rol.CLIENTE,oscuro) {
        if(!listo) CircularProgressIndicator() else {
            LaunchedEffect(usuario?.id) {
                val destino = if(usuario==null) Rutas.LOGIN else if(admin) Rutas.PANEL else Rutas.MENU
                nav.navigate(destino) {popUpTo(nav.graph.id); launchSingleTop=true}
            }
            val entrada by nav.currentBackStackEntryAsState()
            val ruta = entrada?.destination?.route
            val tabs = if(admin) listOf(
Rutas.PANEL to "Panel") else listOf(Rutas.MENU to "Menú" )
            Scaffold(topBar={if(usuario!=null) AppTopBar("CafeCampus", {nav.popBackStack(); Unit})},
                bottomBar={if(usuario!=null) Row {
                    tabs.forEach {(destino,titulo) -> TextButton(onClick={nav.navigate(destino) {launchSingleTop=true}}) {Text(titulo)}}
                    TextButton(onClick=sesion::cambiarPerfil) {Text("Cambiar perfil")}
                }}) {padding ->
                NavHost(nav,startDestination=Rutas.LOGIN,modifier=Modifier.padding(padding)) {
                    composable(Rutas.LOGIN) {LoginScreen(sesion)}
                    composable(Rutas.SPLASH) {SplashScreen({nav.navigate(Rutas.LOGIN)},{nav.navigate(Rutas.LOGIN)})}
                    composable(Rutas.MENU) {MenuScreen(menu,sesion,{nav.navigate(Rutas.producto(it))},{})}
                    composable(Rutas.PRODUCTO,arguments=listOf(navArgument("id") {type=NavType.IntType})) {e ->
                        DetalleProductoScreen(menu,e.arguments!!.getInt("id")) {nav.popBackStack()}
                    }
                    composable(Rutas.PANEL) {Text("El panel se implementa en el Paso 9")}
                }
            }
        }
    }
}
