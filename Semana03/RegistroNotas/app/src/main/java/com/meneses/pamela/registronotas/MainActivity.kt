package com.meneses.pamela.registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meneses.pamela.registronotas.ui.theme.RegistroNotasTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroNotasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaNotas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Curso(val nombre: String, val peso: Float)

@Composable
fun FilaCurso(curso: Curso, nota: Float, onNotaChange: (Float) -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${curso.nombre} (${(curso.peso * 100).toInt()}%)",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = nota.toInt().toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19
        )
    }
}

@Composable
fun PantallaNotas(modifier: Modifier = Modifier) {
    val cursos = listOf(
        Curso("Fundamentos de Programación", 0.20f),
        Curso("Programación Orientada a Objetos", 0.25f),
        Curso("Programación en Móviles", 0.30f),
        Curso("Base de Datos", 0.25f)
    )

    var nota1 by remember { mutableStateOf(0f) }
    var nota2 by remember { mutableStateOf(0f) }
    var nota3 by remember { mutableStateOf(0f) }
    var nota4 by remember { mutableStateOf(0f) }

    var redondearPromedio by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }

    var calculado by remember { mutableStateOf(false) }
    var promPonderado by remember { mutableDoubleStateOf(0.0) }
    var promFinal by remember { mutableDoubleStateOf(0.0) }
    var observacion by remember { mutableStateOf("") }
    var colorChip by remember { mutableStateOf(Color.Gray) }

    fun ejecutarCalculo() {
        val ponderado = (nota1.toInt() * cursos[0].peso) +
                (nota2.toInt() * cursos[1].peso) +
                (nota3.toInt() * cursos[2].peso) +
                (nota4.toInt() * cursos[3].peso)

        promPonderado = ponderado.toDouble()

        val pFinal = if (redondearPromedio) {
            ponderado.roundToInt().toDouble()
        } else {
            ponderado.toDouble()
        }
        promFinal = pFinal

        when {
            pFinal >= 17.0 -> {
                observacion = "EXCELENTE"
                colorChip = Color(0xFF1B5E20)
            }
            pFinal >= 13.0 -> {
                observacion = "APROBADO"
                colorChip = Color(0xFF4CAF50)
            }
            pFinal >= 10.0 -> {
                observacion = "EN RECUPERACIÓN"
                colorChip = Color(0xFFFFB300)
            }
            else -> {
                observacion = "DESAPROBADO"
                colorChip = Color(0xFFE53935)
            }
        }
        calculado = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Registro de Notas",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Notas del ciclo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Desliza para asignar cada nota (0 a 20)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            FilaCurso(curso = cursos[0], nota = nota1, onNotaChange = { nota1 = it })
            Spacer(modifier = Modifier.height(8.dp))
            FilaCurso(curso = cursos[1], nota = nota2, onNotaChange = { nota2 = it })
            Spacer(modifier = Modifier.height(8.dp))
            FilaCurso(curso = cursos[2], nota = nota3, onNotaChange = { nota3 = it })
            Spacer(modifier = Modifier.height(8.dp))
            FilaCurso(curso = cursos[3], nota = nota4, onNotaChange = { nota4 = it })

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final")
                Switch(
                    checked = redondearPromedio,
                    onCheckedChange = { redondearPromedio = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = confirmado,
                    onCheckedChange = { confirmado = it }
                )
                Text("Confirmo que las notas son correctas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { ejecutarCalculo() },
                enabled = confirmado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULAR PROMEDIO")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!calculado) {
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Promedio ponderado:  ${String.format("%.2f", promPonderado)}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Promedio final:  ${if (redondearPromedio) promFinal.toInt().toString() else String.format("%.2f", promFinal)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        if (redondearPromedio) {
                            Text("(redondeado)", fontSize = 12.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = colorChip.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = observacion,
                                color = colorChip,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "✓ Promedio calculado correctamente",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Desarrollado por: Pamela Meneses",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}