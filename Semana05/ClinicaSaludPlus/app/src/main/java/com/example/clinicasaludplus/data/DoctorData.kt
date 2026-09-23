package com.example.clinicasaludplus.data

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double,
    val experienceYears: Int,
    val biography: String
)

data class Appointment(
    val id: Int,
    val doctorName: String,
    val date: String,
    val time: String,
    val status: String
)

object MockData {
    val specialties = listOf("Todos", "Cardiología", "Pediatría", "Dermatología")

    val doctors = listOf(
        Doctor(
            id = 1,
            name = "Dra. Ana Torres",
            specialty = "Cardiología",
            rating = 4.9,
            experienceYears = 12,
            biography = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
        ),
        Doctor(
            id = 2,
            name = "Dr. Luis Vega",
            specialty = "Pediatría",
            rating = 4.7,
            experienceYears = 8,
            biography = "Especialista en desarrollo infantil y pediatría preventiva."
        ),
        Doctor(
            id = 3,
            name = "Dra. Rosa Díaz",
            specialty = "Dermatología",
            rating = 4.8,
            experienceYears = 10,
            biography = "Experta en dermatología clínica y cuidado integral de la piel."
        )
    )

    val dates = listOf("Jue 26", "Vie 27", "Sáb 28")
    val times = listOf("9:00 am", "10:30 am", "3:00 pm")

    val appointmentsList = mutableListOf(
        Appointment(1, "Dra. Ana Torres", "Viernes 27, 10:30 am", "10:30 am", "Confirmada"),
        Appointment(2, "Dr. Luis Vega", "Miércoles 15, 3:00 pm", "3:00 pm", "Completada")
    )
}