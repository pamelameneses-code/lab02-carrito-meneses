package com.example.clinicasaludplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.data.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    doctorId: Int,
    onBackClick: () -> Unit,
    onConfirmClick: (Int, String, String) -> Unit
) {
    // Buscar al médico seleccionado
    val doctor = MockData.doctors.find { it.id == doctorId }

    // Estados locales para controlar la SELECCIÓN ÚNICA de Fecha y Hora
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Cita", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4A148C)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (doctor != null) {
                    Text(
                        text = "Médico seleccionado:",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = doctor.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A148C)
                    )
                    Text(
                        text = doctor.specialty,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- SECCIÓN 1: Seleccionar Fecha ---
                Text(
                    text = "1. Selecciona una Fecha",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(MockData.dates) { date ->
                        FilterChip(
                            selected = (selectedDate == date),
                            onClick = { selectedDate = date }, // Guarda la fecha seleccionada
                            label = { Text(text = date) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4A148C),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- SECCIÓN 2: Seleccionar Hora ---
                Text(
                    text = "2. Selecciona un Horario",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(MockData.times) { time ->
                        FilterChip(
                            selected = (selectedTime == time),
                            onClick = { selectedTime = time }, // Guarda la hora seleccionada
                            label = { Text(text = time) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4A148C),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // --- BOTÓN DE CONFIRMACIÓN ---
            Button(
                enabled = selectedDate.isNotEmpty() && selectedTime.isNotEmpty(),
                onClick = {
                    if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                        // Solo pasamos la navegación a ConfirmationScreen.kt.
                        // ConfirmationScreen ya se encarga de agregarlo a la lista.
                        onConfirmClick(doctorId, selectedDate, selectedTime)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A148C)
                )
            ) {
                Text(
                    text = "Confirmar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}