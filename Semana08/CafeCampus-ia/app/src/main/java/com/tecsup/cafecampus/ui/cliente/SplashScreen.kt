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
    Column(Modifier.fillMaxSize().padding(32.dp),verticalArrangement=Arrangement.Center,horizontalAlignment=Alignment.CenterHorizontally) {
        ProductoIcono("cafe",Modifier.size(140.dp))
        Spacer(Modifier.height(24.dp))
        Text("CafeCampus",style=MaterialTheme.typography.headlineLarge,color=MaterialTheme.colorScheme.primary)
        Text("Tu cafetería, a un toque")
        Spacer(Modifier.height(16.dp))
        Text("Pide desde el campus y recoge sin hacer cola.")
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            listOf("cafe","plato","jugo").forEach { ProductoIcono(it,Modifier.size(80.dp)) }
        }
        Spacer(Modifier.height(40.dp))
        CafeButton("Comenzar",onComenzar,Modifier.fillMaxWidth())
        TextButton(onClick=onLogin) { Text("Ya tengo una cuenta") }
    }
}
