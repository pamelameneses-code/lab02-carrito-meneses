package com.example.clinicasaludplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.components.MainDrawerContent
import com.example.clinicasaludplus.data.Appointment
import com.example.clinicasaludplus.data.MockData
import com.example.clinicasaludplus.ui.theme.DarkText
import com.example.clinicasaludplus.ui.theme.MedicalLightBlue
import com.example.clinicasaludplus.ui.theme.MedicalTurquoise
import com.example.clinicasaludplus.ui.theme.PureWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointmentsScreen(
    currentRoute: String,
    onNavigateToDestination: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val appointments = MockData.appointmentsList
    var appointmentToCancel by remember { mutableStateOf<Appointment?>(null) }

    // Estado para controlar el diálogo de cancelar TODAS las citas
    var showCancelAllDialog by remember { mutableStateOf(false) }

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
                            text = "Mis Citas",
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
                        containerColor = MedicalTurquoise
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Tarjeta de Perfil
                Card(
                    colors = CardDefaults.cardColors(containerColor = MedicalTurquoise),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape),
                            color = PureWhite
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "PM",
                                    color = MedicalTurquoise,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "¡Hola, Pamela Meneses!",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Gestiona tus citas agendadas",
                                color = PureWhite.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // Encabezado con Botón de "Cancelar Todas"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Próximas Citas Médicas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    // Solo muestra el botón si hay al menos una cita
                    if (appointments.isNotEmpty()) {
                        TextButton(onClick = { showCancelAllDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Borrar todas",
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (appointments.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes citas médicas programadas.",
                            color = DarkText,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(appointments, key = { it.id }) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = item.doctorName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = DarkText
                                    )
                                    Text(
                                        text = item.specialty,
                                        color = MedicalTurquoise,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Surface(
                                        shape = MaterialTheme.shapes.small,
                                        color = MedicalLightBlue,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Fecha: ${item.date}",
                                                fontSize = 13.sp,
                                                color = DarkText,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "Hora: ${item.time}",
                                                fontSize = 13.sp,
                                                color = DarkText,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(
                                        onClick = { appointmentToCancel = item },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.errorContainer,
                                            contentColor = MaterialTheme.colorScheme.error
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Cancelar Cita",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Diálogo 1: Confirmar Cancelación de UNA sola cita
            appointmentToCancel?.let { item ->
                AlertDialog(
                    onDismissRequest = { appointmentToCancel = null },
                    title = { Text("Confirmar Cancelación", fontWeight = FontWeight.Bold) },
                    text = { Text("¿Estás segura de que deseas cancelar tu cita con ${item.doctorName} (${item.specialty})?") },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            onClick = {
                                MockData.appointmentsList.removeIf { it.id == item.id }
                                appointmentToCancel = null
                            }
                        ) {
                            Text("Sí, Cancelar", color = PureWhite)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { appointmentToCancel = null }) {
                            Text("Mantener Cita")
                        }
                    }
                )
            }

            // Diálogo 2: Confirmar Cancelación de TODAS las citas
            if (showCancelAllDialog) {
                AlertDialog(
                    onDismissRequest = { showCancelAllDialog = false },
                    title = { Text("¿Cancelar todas las citas?", fontWeight = FontWeight.Bold) },
                    text = { Text("Esta acción eliminará todas las citas programadas de la lista.") },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            onClick = {
                                MockData.appointmentsList.clear() // Vacía toda la lista
                                showCancelAllDialog = false
                            }
                        ) {
                            Text("Sí, Borrar Todas", color = PureWhite)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showCancelAllDialog = false }) {
                            Text("Volver")
                        }
                    }
                )
            }
        }
    }
}