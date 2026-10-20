package com.tecsup.cafecampus.ui.components

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

@Composable fun ProductoIcono(icono: String, modifier: Modifier = Modifier) {
    val vector = when(icono) {
        "cafe" -> Icons.Default.LocalCafe; "plato" -> Icons.Default.Restaurant; "jugo" -> Icons.Default.LocalDrink
        "pan" -> Icons.Default.BakeryDining; "botella" -> Icons.Default.WaterDrop; else -> Icons.Default.Cake
    }
    Surface(color=MaterialTheme.colorScheme.primaryContainer,shape=RoundedCornerShape(12.dp),modifier=modifier) {
        Box(Modifier.padding(20.dp),contentAlignment=Alignment.Center) { Icon(vector,null,Modifier.size(48.dp),tint=MaterialTheme.colorScheme.primary) }
    }
}
