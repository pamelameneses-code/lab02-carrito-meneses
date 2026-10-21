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

@Composable fun SplashScreen(onComenzar: () -> Unit, onLogin: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val altura=maxHeight
        Column(
            Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).heightIn(min=altura).padding(24.dp),
            verticalArrangement=Arrangement.SpaceBetween,
            horizontalAlignment=Alignment.CenterHorizontally
        ) {
            Column(Modifier.fillMaxWidth().padding(top=48.dp),horizontalAlignment=Alignment.CenterHorizontally) {
                Surface(shape=RoundedCornerShape(28.dp),color=MaterialTheme.colorScheme.primary) {
                    Box(Modifier.size(112.dp),contentAlignment=Alignment.Center) {
                        Icon(Icons.Default.LocalCafe,"Logo de CafeCampus",Modifier.size(64.dp),tint=MaterialTheme.colorScheme.onPrimary)
                    }
                }
                Spacer(Modifier.height(28.dp))
                Text("CafeCampus",style=MaterialTheme.typography.headlineLarge)
                Text("Pide sin hacer cola",color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(Modifier.padding(vertical=36.dp),horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                listOf("cafe","plato","jugo").forEach { icono ->
                    Surface(shape=RoundedCornerShape(20.dp),color=MaterialTheme.colorScheme.primaryContainer) {
                        IlustracionProducto(icono,Modifier.size(80.dp))
                    }
                }
            }
            Column(Modifier.fillMaxWidth(),horizontalAlignment=Alignment.CenterHorizontally) {
                CafeButton("Comenzar",onComenzar,Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                TextButton(onClick=onLogin) {Text("Ya tengo una cuenta")}
            }
        }
    }
}
