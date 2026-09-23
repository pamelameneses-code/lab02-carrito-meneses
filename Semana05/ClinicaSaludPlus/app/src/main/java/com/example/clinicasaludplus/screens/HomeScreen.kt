package com.example.clinicasaludplus.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.example.clinicasaludplus.data.Doctor
import com.example.clinicasaludplus.data.MockData
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    currentRoute: String,
    onNavigateToDestination: (String) -> Unit,
    onDoctorSelected: (Int) -> Unit
) {
    // Estado del Drawer (Menú lateral)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Estado local: Guarda la especialidad seleccionada (Por defecto "Todos")
    var selectedSpecialty by remember { mutableStateOf("Todos") }

    // Filtrar médicos según la especialidad seleccionada
    val filteredDoctors = if (selectedSpecialty == "Todos") {
        MockData.doctors
    } else {
        MockData.doctors.filter { it.specialty == selectedSpecialty }
    }

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
                    title = "Clínica Salud+",
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
                // Título de bienvenida
                Text(
                    text = "Encuentra tu especialista",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Filtro Horizontal de Especialidades (LazyRow con FilterChips)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(MockData.specialties) { specialty ->
                        FilterChip(
                            selected = (specialty == selectedSpecialty),
                            onClick = { selectedSpecialty = specialty },
                            label = { Text(text = specialty) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4A148C),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista Vertical de Médicos (LazyColumn)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredDoctors) { doctor ->
                        DoctorItemCard(
                            doctor = doctor,
                            onClick = { onDoctorSelected(doctor.id) }
                        )
                    }
                }
            }
        }
    }
}

// Tarjeta individual para mostrar datos del médico
@Composable
fun DoctorItemCard(
    doctor: Doctor,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono / Avatar del médico
            Surface(
                modifier = Modifier.size(50.dp),
                shape = MaterialTheme.shapes.medium,
                color = Color(0xFFEDE7F6)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = doctor.name.take(2),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A148C)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del médico
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = doctor.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = doctor.specialty,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Calificación",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${doctor.rating} • ${doctor.experienceYears} años exp.",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}