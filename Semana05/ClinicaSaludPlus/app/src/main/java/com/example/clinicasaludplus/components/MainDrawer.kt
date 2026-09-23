package com.example.clinicasaludplus.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.navigation.Screen

// Componente para la barra superior reutilizable con botón de menú
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir Menú",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4A148C) // Color morado principal
        )
    )
}

// Estructura del contenido interno del Menú Lateral (Drawer)
@Composable
fun DrawerContent(
    currentRoute: String,
    onDestinationClicked: (String) -> Unit
) {
    ModalDrawerSheet {
        // Cabecera del usuario en el Drawer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    color = Color(0xFFEDE7F6)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "JP",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A148C)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Juan Pérez",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Paciente",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        // Opciones del Menú Lateral
        DrawerItem(
            label = "Inicio",
            icon = Icons.Default.Home,
            selected = currentRoute == Screen.Home.route,
            onClick = { onDestinationClicked(Screen.Home.route) }
        )
        DrawerItem(
            label = "Mis citas",
            icon = Icons.Default.DateRange,
            selected = currentRoute == Screen.MyAppointments.route,
            onClick = { onDestinationClicked(Screen.MyAppointments.route) }
        )
        DrawerItem(
            label = "Historial médico",
            icon = Icons.Default.History,
            selected = currentRoute == Screen.MedicalHistory.route,
            onClick = { onDestinationClicked(Screen.MedicalHistory.route) }
        )
        DrawerItem(
            label = "Perfil",
            icon = Icons.Default.Person,
            selected = currentRoute == Screen.Profile.route,
            onClick = { onDestinationClicked(Screen.Profile.route) }
        )
    }
}

// Componente para cada botón individual dentro del Drawer
@Composable
private fun DrawerItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = label, fontWeight = FontWeight.Medium) },
        icon = { Icon(imageVector = icon, contentDescription = label) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFFF3E8FF),
            selectedIconColor = Color(0xFF4A148C),
            selectedTextColor = Color(0xFF4A148C)
        )
    )
}