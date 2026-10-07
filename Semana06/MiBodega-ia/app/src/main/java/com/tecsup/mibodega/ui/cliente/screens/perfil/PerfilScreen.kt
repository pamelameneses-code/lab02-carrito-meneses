package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BarraInferior
import com.tecsup.mibodega.ui.theme.VerdeBodega

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    nombre: String,
    telefono: String,
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit,
    onNavegarTab: (Int) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi perfil", fontWeight = FontWeight.Bold) }) },
        bottomBar = { BarraInferior(seleccionado = 3, onNavegar = onNavegarTab) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(VerdeBodega, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(telefono, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Modo oscuro", style = MaterialTheme.typography.bodyLarge)
                Switch(checked = modoOscuro, onCheckedChange = onCambiarModoOscuro)
            }

            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión", color = VerdeBodega, fontWeight = FontWeight.Bold)
            }
        }
    }
}