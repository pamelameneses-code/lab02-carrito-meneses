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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
@Composable fun PedidosAdminScreen(vm: PedidosAdminViewModel, onPedido: (Int) -> Unit) {
    val estado by vm.estadoSeleccionado.collectAsState()
    val pedidos by vm.pedidos.collectAsState()
    val error by vm.error.collectAsState()
    Column(Modifier.fillMaxSize().padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)) {
            EstadoPedido.entries.forEach {e -> FiltroAdmin(if(e==EstadoPedido.RECIBIDO) "Nuevos" else etiquetaEstado(e),estado==e,{vm.estadoSeleccionado.value=e})}
        }
        error?.let {Text(it,fontSize=12.sp,color=MaterialTheme.colorScheme.error)}
        if(pedidos.isEmpty()) Text("No hay pedidos en este estado",fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp),contentPadding=PaddingValues(bottom=16.dp)) {
            items(pedidos,key={it.pedido.id}) {p -> TarjetaPedidoAdmin(p,vm.siguiente(p),vm.puedeCancelar(p),
                {onPedido(p.pedido.id)},{vm.avanzar(p)},{vm.cancelar(p)})}
        }
    }
}
