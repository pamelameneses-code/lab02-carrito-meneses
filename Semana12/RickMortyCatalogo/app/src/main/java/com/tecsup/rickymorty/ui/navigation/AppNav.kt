package com.tecsup.rickymorty.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.tecsup.rickymorty.ui.components.PermisoNotificaciones
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.rickymorty.domain.model.Sesion
import com.tecsup.rickymorty.domain.model.Rol
import com.tecsup.rickymorty.ui.admin.AdminScreen
import com.tecsup.rickymorty.ui.admin.AdminViewModel
import com.tecsup.rickymorty.ui.UiState
import com.tecsup.rickymorty.ui.components.Cargando
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

@Composable
private fun CatalogoNav(
    factory: ViewModelProvider.Factory,
    sesion: Sesion,
    cerrarSesion: () -> Unit,
    modifier: Modifier
) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "lista", modifier = modifier) {
        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = factory)
            Column {
                Text("Sesión: ${sesion.correo}")
                TextButton(onClick = cerrarSesion) { Text("Cerrar sesión") }
                if (sesion.rol == Rol.ADMIN) {
                    TextButton(onClick = { nav.navigate("admin") }) { Text("Panel Admin") }
                }
                TextButton(onClick = { nav.navigate("favoritos") }) { Text("Favoritos") }
                vm.aviso.collectAsStateWithLifecycle().value?.let {
                    Text("$it Mostrando datos guardados.")
                    TextButton(onClick = vm::refrescar) { Text("Reintentar") }
                }
                ListaScreen(
                    vm.estado.collectAsStateWithLifecycle().value,
                    reintentar = vm::refrescar,
                    abrir = { nav.navigate("detalle/$it") }
                )
            }
        }
        if (sesion.rol == Rol.ADMIN) {
            composable("admin") {
                val vm: AdminViewModel = viewModel(factory = factory)
                AdminScreen(
                    vm.estado.collectAsStateWithLifecycle().value,
                    vm.ocupado.collectAsStateWithLifecycle().value,
                    vm.mensaje.collectAsStateWithLifecycle().value,
                    { nav.popBackStack() },
                    vm::sincronizar,
                    vm::limpiarCache
                )
            }
        }
        composable("favoritos") {
            val vm: FavoritosViewModel = viewModel(factory = factory)
            FavoritosScreen(
                vm.estado.collectAsStateWithLifecycle().value,
                { nav.popBackStack() },
                { nav.navigate("detalle/$it") }
            )
        }
        composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            val vm: DetalleViewModel = viewModel(factory = factory)
            DetalleScreen(vm.estado.collectAsStateWithLifecycle().value, { nav.popBackStack() }, vm::cargar, vm::alternarFavorito)
        }
    }
}
