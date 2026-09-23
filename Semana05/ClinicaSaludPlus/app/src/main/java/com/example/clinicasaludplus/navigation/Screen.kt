package com.example.clinicasaludplus.navigation

// Sealed class para definir todas las rutas posibles de la aplicación
sealed class Screen(val route: String) {
    // Rutas del menú lateral (Drawer) y principales
    object Home : Screen("home")
    object MyAppointments : Screen("my_appointments")
    object MedicalHistory : Screen("medical_history")
    object Profile : Screen("profile")

    // Rutas del flujo secuencial con parámetros
    object DoctorDetail : Screen("doctor_detail/{doctorId}") {
        fun createRoute(doctorId: Int) = "doctor_detail/$doctorId"
    }

    object Schedule : Screen("schedule/{doctorId}") {
        fun createRoute(doctorId: Int) = "schedule/$doctorId"
    }

    object Confirmation : Screen("confirmation/{doctorId}/{date}/{time}") {
        fun createRoute(doctorId: Int, date: String, time: String) =
            "confirmation/$doctorId/$date/$time"
    }
}