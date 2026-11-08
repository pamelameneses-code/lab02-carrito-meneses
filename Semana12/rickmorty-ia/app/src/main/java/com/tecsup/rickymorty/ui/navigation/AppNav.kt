package com.tecsup.rickymorty.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.rickymorty.R
import com.tecsup.rickymorty.domain.model.Rol
import com.tecsup.rickymorty.domain.model.Sesion
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.admin.AdminScreen
import com.tecsup.rickymorty.ui.admin.AdminViewModel
import com.tecsup.rickymorty.ui.components.Cargando
import com.tecsup.rickymorty.ui.components.PermisoNotificaciones
import com.tecsup.rickymorty.ui.detalle.DetalleScreen
import com.tecsup.rickymorty.ui.detalle.DetalleViewModel
import com.tecsup.rickymorty.ui.favoritos.FavoritosScreen
import com.tecsup.rickymorty.ui.favoritos.FavoritosViewModel
import com.tecsup.rickymorty.ui.lista.ListaScreen
import com.tecsup.rickymorty.ui.lista.ListaViewModel
import com.tecsup.rickymorty.ui.login.LoginScreen
import com.tecsup.rickymorty.ui.login.LoginViewModel

@Composable
fun AppNav(factory: ViewModelProvider.Factory, modifier: Modifier = Modifier) {
    val login: LoginViewModel = viewModel(factory = factory)
    when (val estado = login.estado.collectAsStateWithLifecycle().value) {
        UiState.Cargando -> Column(modifier) { Cargando() }
        is UiState.Error -> LoginScreen(estado.mensaje, login::iniciarSesion, modifier)
        is UiState.Exito -> {
            val sesion = estado.datos
            if (sesion == null) LoginScreen(null, login::iniciarSesion, modifier)
            else key(sesion.uid) {
                PermisoNotificaciones {
                    CatalogoNav(factory, sesion, login::cerrarSesion, modifier)
                }
            }
        }
    }
}

private data class Pestaña(val ruta: String, val titulo: String, val icono: Int)

@Composable
private fun CatalogoNav(
    factory: ViewModelProvider.Factory,
    sesion: Sesion,
    cerrarSesion: () -> Unit,
    modifier: Modifier
) {
    val nav = rememberNavController()
    val ruta = nav.currentBackStackEntryAsState().value?.destination?.route ?: "lista"
    val pestanas = buildList {
        add(Pestaña("lista", "Catálogo", R.drawable.ic_catalogo))
        add(Pestaña("favoritos", "Favoritos", R.drawable.ic_favorito))
        if (sesion.rol == Rol.ADMIN) add(Pestaña("admin", "Panel Admin", R.drawable.ic_panel))
    }
    fun navegarPestana(destino: String) {
        if (destino == ruta) return
        nav.navigate(destino) {
            popUpTo("lista") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (pestanas.any { it.ruta == ruta }) {
                NavigationBar(windowInsets = WindowInsets(0, 0, 0, 0)) {
                    pestanas.forEach { pestana ->
                        NavigationBarItem(
                            selected = pestana.ruta == ruta,
                            onClick = { navegarPestana(pestana.ruta) },
                            icon = { Icon(painterResource(pestana.icono), null) },
                            label = { Text(pestana.titulo) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = "lista", modifier = Modifier.padding(padding)) {
            composable("lista") {
                val vm: ListaViewModel = viewModel(factory = factory)
                ListaScreen(
                    estado = vm.resultados.collectAsStateWithLifecycle().value,
                    busqueda = vm.busqueda.collectAsStateWithLifecycle().value,
                    aviso = vm.aviso.collectAsStateWithLifecycle().value,
                    buscar = vm::buscar,
                    reintentar = vm::refrescar,
                    abrir = { nav.navigate("detalle/$it") },
                    alternarFavorito = vm::alternarFavorito,
                    cerrarSesion = cerrarSesion
                )
            }
            if (sesion.rol == Rol.ADMIN) {
                composable("admin") {
                    val vm: AdminViewModel = viewModel(factory = factory)
                    AdminScreen(
                        vm.estado.collectAsStateWithLifecycle().value,
                        vm.ocupado.collectAsStateWithLifecycle().value,
                        vm.mensaje.collectAsStateWithLifecycle().value,
                        { navegarPestana("lista") },
                        vm::sincronizar,
                        vm::limpiarCache
                    )
                }
            }
            composable("favoritos") {
                val vm: FavoritosViewModel = viewModel(factory = factory)
                FavoritosScreen(
                    estado = vm.estado.collectAsStateWithLifecycle().value,
                    volver = { navegarPestana("lista") },
                    abrir = { nav.navigate("detalle/$it") },
                    alternarFavorito = vm::alternarFavorito
                )
            }
            composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
                val vm: DetalleViewModel = viewModel(factory = factory)
                DetalleScreen(vm.estado.collectAsStateWithLifecycle().value, { nav.popBackStack() }, vm::cargar, vm::alternarFavorito)
            }
        }
    }
}
