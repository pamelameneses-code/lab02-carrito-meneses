package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*
import com.tecsup.cafecampus.ui.cliente.IlustracionProducto
import com.tecsup.cafecampus.ui.cliente.ChipDisponible
import com.tecsup.cafecampus.ui.cliente.ChipEstadoEstudiante
import com.tecsup.cafecampus.ui.cliente.fechaPedido
import com.tecsup.cafecampus.ui.cliente.numeroPedido
import com.tecsup.cafecampus.ui.theme.colorEstado
import com.tecsup.cafecampus.viewmodel.*

@Composable fun AjustesScreen(vm: SesionViewModel, onCambiar: () -> Unit, onCerrar: () -> Unit) {
    val usuario by vm.usuarioActual.collectAsState()
    if(usuario?.rol!=Rol.ADMIN.name) {PerfilPractica(vm,onCambiar,onCerrar);return}
    val oscuro by vm.modoOscuro.collectAsState()
    val abierto by vm.recibirPedidos.collectAsState()
    val notificaciones by vm.notificaciones.collectAsState()
    val error by vm.error.collectAsState()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        TarjetaAdmin {Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            AvatarAdmin(Modifier.size(52.dp))
            Column(verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text(usuario?.nombre ?: "Administrador",fontSize=15.sp,fontWeight=FontWeight.Bold)
                Text(usuario?.correo.orEmpty(),fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Surface(color=MaterialTheme.colorScheme.primaryContainer,shape=RoundedCornerShape(50)) {
                    Text("Perfil activo",Modifier.padding(horizontal=8.dp,vertical=3.dp),fontSize=9.sp,color=MaterialTheme.colorScheme.primary)
                }
            }
        }}
        Surface(onClick=onCambiar,color=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary,shape=RoundedCornerShape(12.dp)) {
            Row(Modifier.fillMaxWidth().padding(14.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                Icon(Icons.Default.SwapHoriz,null,Modifier.size(26.dp))
                Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                    Text("Cambiar de perfil",fontSize=14.sp,fontWeight=FontWeight.Bold)
                    Text("Ingresar con la cuenta de Estudiante",fontSize=10.sp)
                }
                Icon(Icons.Default.ChevronRight,null,Modifier.size(20.dp))
            }
        }
        Text("Preferencias",fontSize=14.sp,fontWeight=FontWeight.Bold)
        TarjetaAdmin {
            PreferenciaAdmin("Modo oscuro","Cambia el tema de la app",oscuro,vm::guardarModoOscuro)
            HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant)
            PreferenciaAdmin("Notificaciones","Avisar nuevos pedidos",notificaciones,vm::guardarNotificaciones)
            HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant)
            PreferenciaAdmin("Recibir pedidos","Abrir o cerrar la cafetería",abierto,vm::guardarRecibirPedidos)
        }
        Surface(color=MaterialTheme.colorScheme.primaryContainer.copy(alpha=.35f),shape=RoundedCornerShape(12.dp)) {
            Column(Modifier.fillMaxWidth().padding(14.dp),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text("Se guardan en el dispositivo",fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Text("con DataStore",fontSize=11.sp,fontWeight=FontWeight.SemiBold,color=MaterialTheme.colorScheme.primary)
            }
        }
        error?.let {Text(it,fontSize=12.sp,color=MaterialTheme.colorScheme.error)}
        OutlinedButton(onClick=onCerrar,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(10.dp),border=BorderStroke(1.dp,MaterialTheme.colorScheme.error)) {
            Text("Cerrar sesión",fontSize=13.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.error)
        }
    }
}
@Composable private fun PreferenciaAdmin(titulo: String, descripcion: String, valor: Boolean, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment=Alignment.CenterVertically) {
        Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
            Text(titulo,fontSize=12.sp,fontWeight=FontWeight.Bold)
            Text(descripcion,fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked=valor,onCheckedChange=onChange)
    }
}
@Composable private fun PerfilPractica(vm: SesionViewModel, onCambiar: () -> Unit, onCerrar: () -> Unit) {
    val oscuro by vm.modoOscuro.collectAsState()
    val abierto by vm.recibirPedidos.collectAsState()
    val usuario by vm.usuarioActual.collectAsState()
    val error by vm.error.collectAsState()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        Text(if(usuario?.rol==Rol.ADMIN.name) "Ajustes" else "Mi perfil",style=MaterialTheme.typography.headlineSmall)
        CafeCard { Icon(Icons.Default.AccountCircle,null,Modifier.size(48.dp),tint=MaterialTheme.colorScheme.primary); Text(usuario?.nombre.orEmpty(),style=MaterialTheme.typography.titleLarge); Text(usuario?.correo.orEmpty()) }
        CafeCard {
            Row(verticalAlignment=Alignment.CenterVertically) { Icon(Icons.Default.DarkMode,null); Text("Modo oscuro",Modifier.weight(1f).padding(start=12.dp)); Switch(checked=oscuro,onCheckedChange=vm::guardarModoOscuro) }
            if(usuario?.rol==Rol.ADMIN.name) Row(verticalAlignment=Alignment.CenterVertically) { Icon(Icons.Default.Store,null); Text("Recibir pedidos",Modifier.weight(1f).padding(start=12.dp)); Switch(checked=abierto,onCheckedChange=vm::guardarRecibirPedidos) }
        }
        CafeCard { Text("Modo práctica",style=MaterialTheme.typography.titleMedium); Text("Al cambiar de perfil se solicitarán el correo y la contraseña de la otra cuenta.") }
        error?.let { Text(it,color=MaterialTheme.colorScheme.error) }
        CafeButton("Cambiar a ${if(usuario?.rol==Rol.ADMIN.name) "Estudiante" else "Administrador"}",onCambiar,Modifier.fillMaxWidth())
        OutlinedButton(onClick=onCerrar,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(10.dp)) { Text("Cerrar sesión") }
    }
}
