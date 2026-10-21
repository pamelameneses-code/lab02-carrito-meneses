package com.tecsup.mibodega.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

private data class ItemBarra(val titulo: String, val icono: ImageVector)

private val items = listOf(
    ItemBarra("Inicio", Icons.Default.Home),
    ItemBarra("Favoritos", Icons.Default.Favorite),
    ItemBarra("Pedidos", Icons.Default.ShoppingCart),
    ItemBarra("Perfil", Icons.Default.Person)
)

@Composable
fun BarraInferior(
    seleccionado: Int,
    onNavegar: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { indice, item ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = { onNavegar(indice) },
                icon = { Icon(item.icono, contentDescription = item.titulo) },
                label = { Text(item.titulo) }
            )
        }
    }
}