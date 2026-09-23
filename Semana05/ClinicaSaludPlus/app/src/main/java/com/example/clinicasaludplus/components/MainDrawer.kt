package com.example.clinicasaludplus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.clinicasaludplus.navigation.Screen
import com.example.clinicasaludplus.ui.theme.DarkText
import com.example.clinicasaludplus.ui.theme.MedicalBlue
import com.example.clinicasaludplus.ui.theme.MedicalLightBlue
import com.example.clinicasaludplus.ui.theme.PureWhite

@Composable
fun MainDrawerContent(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = PureWhite
    ) {
        // Cabecera del Usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MedicalBlue)
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    color = PureWhite
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "JP",
                            color = MedicalBlue,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Juan Pérez",
                        color = PureWhite,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Paciente",
                        color = MedicalLightBlue,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones del Menú

        // 1. Inicio
        NavigationDrawerItem(
            label = { Text("Inicio", color = DarkText) },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                onCloseDrawer()
                onNavigate(Screen.Home.route)
            },
            icon = { Icon(Icons.Default.Home, contentDescription = null, tint = MedicalBlue) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        // 2. Mis Citas
        NavigationDrawerItem(
            label = { Text("Mis citas", color = DarkText) },
            selected = currentRoute == Screen.MyAppointments.route,
            onClick = {
                onCloseDrawer()
                onNavigate(Screen.MyAppointments.route)
            },
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = MedicalBlue) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        // 3. Historial Médico (Navega a MedicalHistory)
        NavigationDrawerItem(
            label = { Text("Historial médico", color = DarkText) },
            selected = currentRoute == Screen.MedicalHistory.route,
            onClick = {
                onCloseDrawer()
                onNavigate(Screen.MedicalHistory.route)
            },
            icon = { Icon(Icons.Default.History, contentDescription = null, tint = MedicalBlue) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        // 4. Perfil (Navega a Profile)
        NavigationDrawerItem(
            label = { Text("Perfil", color = DarkText) },
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                onCloseDrawer()
                onNavigate(Screen.Profile.route)
            },
            icon = { Icon(Icons.Default.Person, contentDescription = null, tint = MedicalBlue) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )
    }
}