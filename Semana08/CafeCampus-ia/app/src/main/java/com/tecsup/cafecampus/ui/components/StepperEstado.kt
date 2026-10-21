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
@Composable fun StepperEstado(estado: EstadoPedido) {
    if(estado == EstadoPedido.CANCELADO) { EstadoChip(estado); return }
    val pasos = listOf(EstadoPedido.RECIBIDO,EstadoPedido.PREPARANDO,EstadoPedido.LISTO,EstadoPedido.ENTREGADO)
    Column(verticalArrangement=Arrangement.spacedBy(12.dp)) {
        pasos.forEachIndexed { i,paso ->
            val activo = i <= pasos.indexOf(estado)
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                Icon(if(activo) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,null,
                    tint=if(activo) colorEstado(paso) else MaterialTheme.colorScheme.outline)
                Text(etiquetaEstado(paso),color=if(activo) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
