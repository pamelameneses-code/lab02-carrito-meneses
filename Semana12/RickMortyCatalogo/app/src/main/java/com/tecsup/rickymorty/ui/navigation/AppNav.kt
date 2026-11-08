package com.tecsup.rickymorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.rickymorty.ui.detalle.DetalleScreen
import com.tecsup.rickymorty.ui.detalle.DetalleViewModel
import com.tecsup.rickymorty.ui.lista.ListaScreen
import com.tecsup.rickymorty.ui.lista.ListaViewModel

@Composable
fun AppNav(factory: ViewModelProvider.Factory, modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "lista", modifier = modifier) {
        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = factory)
            ListaScreen(
                vm.estado.collectAsStateWithLifecycle().value,
                reintentar = vm::refrescar,
                abrir = { nav.navigate("detalle/$it") }
            )
        }
        composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            val vm: DetalleViewModel = viewModel(factory = factory)
            DetalleScreen(vm.estado.collectAsStateWithLifecycle().value, { nav.popBackStack() }, vm::cargar)
        }
    }
}
