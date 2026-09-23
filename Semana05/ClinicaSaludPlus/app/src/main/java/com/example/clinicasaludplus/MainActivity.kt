package com.example.clinicasaludplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clinicasaludplus.navigation.Screen
import com.example.clinicasaludplus.screens.ConfirmationScreen
import com.example.clinicasaludplus.screens.DoctorDetailScreen
import com.example.clinicasaludplus.screens.HomeScreen
import com.example.clinicasaludplus.screens.MyAppointmentsScreen
import com.example.clinicasaludplus.screens.ScheduleScreen
import com.example.clinicasaludplus.ui.theme.ClinicaSaludPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClinicaSaludPlusTheme {
                // Se llama al método Composable de la clase
                AppNavigationContent()
            }
        }
    }

    // Al estar DENTRO de la clase MainActivity, no chocará con ninguna función de otro archivo
    @Composable
    private fun AppNavigationContent() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    currentRoute = Screen.Home.route,
                    onNavigateToDestination = { route ->
                        if (route != Screen.Home.route) {
                            navController.navigate(route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    onDoctorSelected = { doctorId ->
                        navController.navigate(Screen.DoctorDetail.createRoute(doctorId))
                    }
                )
            }

            composable(Screen.MyAppointments.route) {
                MyAppointmentsScreen(
                    currentRoute = Screen.MyAppointments.route,
                    onNavigateToDestination = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(
                route = Screen.DoctorDetail.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                DoctorDetailScreen(
                    doctorId = doctorId,
                    onBackClick = { navController.popBackStack() },
                    onScheduleClick = { id ->
                        navController.navigate(Screen.Schedule.createRoute(id))
                    }
                )
            }

            composable(
                route = Screen.Schedule.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                ScheduleScreen(
                    doctorId = doctorId,
                    onBackClick = { navController.popBackStack() },
                    onConfirmClick = { id, date, time ->
                        navController.navigate(Screen.Confirmation.createRoute(id, date, time))
                    }
                )
            }

            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.IntType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("time") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val time = backStackEntry.arguments?.getString("time") ?: ""

                ConfirmationScreen(
                    doctorId = doctorId,
                    date = date,
                    time = time,
                    onHomeClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}