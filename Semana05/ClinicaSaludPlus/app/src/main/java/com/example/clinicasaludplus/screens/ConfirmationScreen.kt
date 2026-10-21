package com.example.clinicasaludplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinicasaludplus.data.Appointment
import com.example.clinicasaludplus.data.MockData

@Composable
fun ConfirmationScreen(
    doctorId: Int,
    date: String,
    time: String,
    onHomeClick: () -> Unit
) {
    val doctor = MockData.doctors.find { it.id == doctorId }

    // Registra la nueva cita en MockData al cargar la pantalla por primera vez
    LaunchedEffect(Unit) {
        if (doctor != null) {
            // Verificamos si la cita ya fue registrada para no duplicarla si se recompone la pantalla
            val yaExiste = MockData.appointmentsList.any {
                it.doctorName == doctor.name && it.date == date && it.time == time
            }

            if (!yaExiste) {
                val newAppointment = Appointment(
                    id = System.currentTimeMillis().toInt(), // ID único basado en el tiempo
                    doctorName = doctor.name,
                    specialty = doctor.specialty,
                    date = date,
                    time = time,
                    status = "Confirmada"
                )
                MockData.appointmentsList.add(newAppointment)
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícono verde de éxito
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Éxito",
                    tint = Color(0xFF2E7D32), // Color verde
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Cita Agendada con Éxito!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tu cita médica ha sido registrada en el sistema de la clínica.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Tarjeta con el resumen de la cita
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Detalles de la Reserva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF4A148C)
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.LightGray
                        )
                        Text(
                            text = "Médico:",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = doctor?.name ?: "Médico",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Especialidad:",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = doctor?.specialty ?: "-",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Fecha:",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = date,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            Column {
                                Text(
                                    text = "Hora:",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = time,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Botón para finalizar e ir al inicio
            Button(
                onClick = onHomeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A148C)
                )
            ) {
                Text(
                    text = "Volver al Inicio",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}