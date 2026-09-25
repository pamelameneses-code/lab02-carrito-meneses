package com.example.clinicasaludplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.components.MainDrawerContent
import com.example.clinicasaludplus.data.Doctor
import com.example.clinicasaludplus.data.MockData
import com.example.clinicasaludplus.ui.theme.DarkText
import com.example.clinicasaludplus.ui.theme.MedicalBlue
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

    // 1. Estado dinámico de la lista de citas
    var appointmentList by remember {
        mutableStateOf(MockData.doctors.take(2))
    }

    // 2. Estado para controlar qué cita se va a cancelar mediante el AlertDialog
    var doctorToCancel by remember { mutableStateOf<Doctor?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    onNavigateToDestination(route)
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
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
                        containerColor = MedicalBlue
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
                Text(
                    text = "Próximas Citas Médicas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedicalBlue
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (appointmentList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes citas médicas programadas.",
                            color = DarkText,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(appointmentList) { doctor ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = doctor.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = DarkText
                                    )
                                    Text(
                                        text = doctor.specialty,
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
                                                text = "Fecha: Jue 26",
                                                fontSize = 13.sp,
                                                color = DarkText,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "Hora: 09:00 AM",
                                                fontSize = 13.sp,
                                                color = DarkText,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Botón para desplegar el AlertDialog
                                    Button(
                                        onClick = { doctorToCancel = doctor },
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

            // 3. Cuadro de diálogo modal (AlertDialog)
            doctorToCancel?.let { doctor ->
                AlertDialog(
                    onDismissRequest = { doctorToCancel = null },
                    title = {
                        Text(
                            text = "Confirmar Cancelación",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = "¿Estás segura de que deseas cancelar tu cita con ${doctor.name} (${doctor.specialty})?"
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = {
                                appointmentList = appointmentList.filter { it.id != doctor.id }
                                doctorToCancel = null
                            }
                        ) {
                            Text("Sí, Cancelar", color = PureWhite)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { doctorToCancel = null }) {
                            Text("Mantener Cita")
                        }
                    }
                )
            }
        }
    }
}