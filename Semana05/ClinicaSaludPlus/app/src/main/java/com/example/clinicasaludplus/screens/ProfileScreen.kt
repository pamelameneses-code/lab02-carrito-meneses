package com.example.clinicasaludplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.components.MainDrawerContent
import com.example.clinicasaludplus.ui.theme.DarkText
import com.example.clinicasaludplus.ui.theme.MedicalBlue
import com.example.clinicasaludplus.ui.theme.MedicalLightBlue
import com.example.clinicasaludplus.ui.theme.MedicalTurquoise
import com.example.clinicasaludplus.ui.theme.PureWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    currentRoute: String,
    onNavigateToDestination: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route -> onNavigateToDestination(route) },
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Mi Perfil",
                            color = PureWhite,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = PureWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MedicalBlue
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar del Paciente
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MedicalBlue
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "JP",
                            color = PureWhite,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Pamela Meneses",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Text(
                    text = "ID Paciente: #SALUD-2026-09",
                    fontSize = 14.sp,
                    color = MedicalTurquoise,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Detalles de contacto e información
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProfileInfoRow(
                            icon = Icons.Default.Person,
                            label = "DNI",
                            value = "74839201"
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = MedicalLightBlue)
                        ProfileInfoRow(
                            icon = Icons.Default.Email,
                            label = "Correo",
                            value = "pamela.meneses@email.com"
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = MedicalLightBlue)
                        ProfileInfoRow(
                            icon = Icons.Default.Phone,
                            label = "Teléfono",
                            value = "+51 987 654 321"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MedicalBlue,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = DarkText.copy(alpha = 0.6f))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkText)
        }
    }
}