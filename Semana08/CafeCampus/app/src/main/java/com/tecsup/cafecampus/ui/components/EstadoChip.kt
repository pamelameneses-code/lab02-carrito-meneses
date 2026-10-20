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

import com.tecsup.cafecampus.ui.theme.colorEstado
fun etiquetaEstado(estado: EstadoPedido) = when(estado) {
    EstadoPedido.RECIBIDO -> "Recibido"; EstadoPedido.PREPARANDO -> "Preparando"; EstadoPedido.LISTO -> "Listo"
    EstadoPedido.ENTREGADO -> "Entregado"; EstadoPedido.CANCELADO -> "Cancelado"
}
@Composable fun EstadoChip(estado: EstadoPedido) {
    val color = colorEstado(estado)
    Surface(color=color.copy(alpha=0.12f),contentColor=color,shape=RoundedCornerShape(20.dp)) {
        Text(etiquetaEstado(estado),modifier=Modifier.padding(horizontal=12.dp,vertical=6.dp),style=MaterialTheme.typography.labelMedium)
    }
}
