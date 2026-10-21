package com.tecsup.cafecampus.ui.cliente

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
import androidx.compose.ui.unit.dp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*

import com.tecsup.cafecampus.viewmodel.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items

import com.tecsup.cafecampus.ui.theme.CafeTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun LoginScreen(vm: SesionViewModel) {
    val rol by vm.rolLogin.collectAsState()
    var correo by rememberSaveable(rol) { mutableStateOf("") }
    var clave by rememberSaveable(rol) { mutableStateOf("") }
    val error by vm.error.collectAsState()
    val cargando by vm.cargando.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()
    CafeTheme(rol,oscuro) {
        Surface(Modifier.fillMaxSize(),color=MaterialTheme.colorScheme.background) {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.height(20.dp))
                ProductoIcono("cafe",Modifier.align(Alignment.CenterHorizontally))
                Text("Bienvenido a CafeCampus",style=MaterialTheme.typography.headlineSmall)
                Text("Ingresa para continuar")
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                    listOf(Rol.CLIENTE,Rol.ADMIN).forEachIndexed { index,r ->
                        SegmentedButton(selected=rol==r,onClick={ vm.seleccionarRol(r) },shape=SegmentedButtonDefaults.itemShape(index,2)) {
                            Text(if(r==Rol.CLIENTE) "Estudiante" else "Administrador")
                        }
                    }
                }
                CafeTextField(correo,{correo=it},"Correo electrónico",Icons.Default.Email)
                CafeTextField(clave,{clave=it},"Contraseña",Icons.Default.Lock,password=true)
                Text("¿Olvidaste tu contraseña?",color=MaterialTheme.colorScheme.primary,modifier=Modifier.align(Alignment.End))
                error?.let { Text(it,color=MaterialTheme.colorScheme.error) }
                CafeButton(if(cargando) "Ingresando…" else "Ingresar",{vm.login(correo,clave,rol)},Modifier.fillMaxWidth(),!cargando)
                if(rol==Rol.CLIENTE) Text("¿No tienes cuenta? Regístrate",Modifier.align(Alignment.CenterHorizontally),color=MaterialTheme.colorScheme.primary)
                if(rol==Rol.ADMIN) Surface(color=MaterialTheme.colorScheme.primaryContainer,shape=RoundedCornerShape(12.dp)) {
                    Column(Modifier.padding(16.dp)) { Text("Modo práctica",style=MaterialTheme.typography.titleMedium); Text("Para cambiar de perfil debes ingresar con el correo y la contraseña de esa cuenta.") }
                }
            }
        }
    }
}
