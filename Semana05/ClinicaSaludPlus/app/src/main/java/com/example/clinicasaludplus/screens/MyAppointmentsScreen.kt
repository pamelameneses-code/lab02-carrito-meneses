package com.example.clinicasaludplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.components.DrawerContent
import com.example.clinicasaludplus.components.TopBar
import com.example.clinicasaludplus.data.Appointment
import com.example.clinicasaludplus.data.MockData
import kotlinx.coroutines.launch

@Composable
fun MyAppointmentsScreen(
    currentRoute: String,
    onNavigateToDestination: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onDestinationClicked = { route ->
                    scope.launch { drawerState.close() }
                    onNavigateToDestination(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Mis Citas",
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Citas Programadas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (MockData.appointmentsList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes citas agendadas.",
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(MockData.appointmentsList) { appointment ->
                            AppointmentItemCard(appointment = appointment)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentItemCard(appointment: Appointment) {
    // Determinar el color según el estado de la cita (RF04)
    val (statusColor, containerColor) = when (appointment.status) {
        "Confirmada" -> Color(0xFF2E7D32) to Color(0xFFE8F5E9)
        "Pendiente" -> Color(0xFFE65100) to Color(0xFFFFF3E0)
        "Cancelada" -> Color(0xFFC62828) to Color(0xFFFFEBEE)
        else -> Color.Gray to Color(0xFFF5F5F5)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = appointment.doctorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4A148C)
                )

                // Chip / Badge indicador de Estado
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = containerColor
                ) {
                    Text(
                        text = appointment.status,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fecha: ${appointment.date}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = "Hora: ${appointment.time}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}