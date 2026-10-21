package com.tecsup.tecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    destinoSeleccionado: String,
    contadorFavoritos: Int,
    onNavegar: (String) -> Unit
) {
    ModalDrawerSheet {
        // Cabecera del usuario personalizada a Pamela Meneses
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "PM", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Pamela Meneses", style = MaterialTheme.typography.titleMedium)
                Text(text = "pamela.meneses@tecsup.edu.pe", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

        // Opciones del NavigationDrawer con íconos circulares
        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = destinoSeleccionado == "Inicio",
            onClick = { onNavegar("Inicio") },
            icon = { Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Mis pedidos") },
            selected = destinoSeleccionado == "Mis pedidos",
            onClick = { onNavegar("Mis pedidos") },
            icon = { Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Favoritos") },
            selected = destinoSeleccionado == "Favoritos",
            onClick = { onNavegar("Favoritos") },
            icon = { Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null) },
            badge = {
                if (contadorFavoritos > 0) {
                    Badge {
                        Text(text = contadorFavoritos.toString())
                    }
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Perfil") },
            selected = destinoSeleccionado == "Perfil",
            onClick = { onNavegar("Perfil") },
            icon = { Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Cerrar sesion") },
            selected = destinoSeleccionado == "Cerrar sesion",
            onClick = { onNavegar("Cerrar sesion") },
            icon = { Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}