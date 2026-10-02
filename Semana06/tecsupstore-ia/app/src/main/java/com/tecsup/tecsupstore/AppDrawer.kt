package com.tecsup.tecsupstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    destinoActual: String,
    onNavegar: (String) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MR",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Pamela Meneses", style = MaterialTheme.typography.titleMedium)
            Text(text = "pamela@tecsup.edu.pe", style = MaterialTheme.typography.bodySmall)
        }

        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = destinoActual == "inicio",
            onClick = { onNavegar("inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Mis pedidos") },
            selected = destinoActual == "pedidos",
            onClick = { onNavegar("pedidos") },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Favoritos") },
            selected = destinoActual == "favoritos",
            onClick = { onNavegar("favoritos") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Perfil") },
            selected = destinoActual == "perfil",
            onClick = { onNavegar("perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
            modifier = Modifier.padding(12.dp)
        )
    }
}