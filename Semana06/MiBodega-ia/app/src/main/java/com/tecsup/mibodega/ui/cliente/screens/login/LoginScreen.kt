package com.tecsup.mibodega.ui.cliente.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

private const val USUARIO_VALIDO = "pamela"
private const val CLAVE_VALIDA = "1234"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onVolver: () -> Unit,
    onIngresar: (String) -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var intento by remember { mutableStateOf(false) }
    var credencialesInvalidas by remember { mutableStateOf(false) }

    val usuarioVacio = intento && usuario.isBlank()
    val claveVacia = intento && clave.isBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar sesión") },
                navigationIcon = { TextButton(onClick = onVolver) { Text("Volver") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it; credencialesInvalidas = false },
                label = { Text("Usuario") },
                singleLine = true,
                isError = usuarioVacio || credencialesInvalidas,
                supportingText = { if (usuarioVacio) Text("Ingresa tu usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it; credencialesInvalidas = false },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                isError = claveVacia || credencialesInvalidas,
                supportingText = {
                    when {
                        claveVacia -> Text("Ingresa tu contraseña")
                        credencialesInvalidas -> Text("Usuario o contraseña incorrectos")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    intento = true
                    if (usuario.isNotBlank() && clave.isNotBlank()) {
                        if (usuario.trim() == USUARIO_VALIDO && clave == CLAVE_VALIDA) {
                            onIngresar(usuario.trim())
                        } else {
                            credencialesInvalidas = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }
        }
    }
}