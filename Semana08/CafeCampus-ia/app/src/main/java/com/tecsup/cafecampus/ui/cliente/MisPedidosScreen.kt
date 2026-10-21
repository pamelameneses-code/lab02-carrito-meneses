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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*
import com.tecsup.cafecampus.viewmodel.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable fun MisPedidosScreen(vm: PedidosClienteViewModel, onPedido: (Int) -> Unit) {
    val visibles by vm.visibles.collectAsState()
    val activos by vm.mostrarActivos.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        Surface(shape=RoundedCornerShape(12.dp),color=MaterialTheme.colorScheme.surfaceVariant.copy(alpha=.5f)) {
            Row(Modifier.fillMaxWidth().padding(4.dp)) {
                listOf(true to "Activos",false to "Historial").forEach { (valor,titulo) ->
                    Surface(onClick={vm.mostrarActivos.value=valor},modifier=Modifier.weight(1f),shape=RoundedCornerShape(10.dp),
                        color=if(activos==valor) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha=0f)) {
                        Box(Modifier.height(32.dp),contentAlignment=Alignment.Center) {
                            Text(titulo,fontSize=12.sp,fontWeight=FontWeight.Bold,color=if(activos==valor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
        if(visibles.isEmpty()) Text("No hay pedidos en esta sección",fontSize=13.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp),contentPadding=PaddingValues(bottom=12.dp)) {
            items(visibles,key={it.pedido.id}) {p ->
                TarjetaEstudiante(Modifier.clickable {onPedido(p.pedido.id)}) {
                    Row(verticalAlignment=Alignment.CenterVertically) {
                        Text("Pedido #${numeroPedido(p.pedido.id)}",Modifier.weight(1f),fontSize=14.sp,fontWeight=FontWeight.Bold)
                        Icon(Icons.Default.ChevronRight,null,Modifier.size(18.dp),tint=MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(vm.resumen(p),fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant,maxLines=1,overflow=TextOverflow.Ellipsis)
                    Text(fechaPedido(p.pedido.fecha),fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=.75f))
                    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
                        ChipEstadoEstudiante(EstadoPedido.valueOf(p.pedido.estado))
                        Text(dinero(p.pedido.total),fontSize=13.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
