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

@Composable fun DetallePedidoAdminScreen(vm: PedidosAdminViewModel, id: Int) {
    val flujo=remember(vm,id) {vm.pedido(id)}
    val pedido by flujo.collectAsState()
    val error by vm.error.collectAsState()
    val p=pedido
    if(p==null) {Text("Cargando pedido…",Modifier.padding(16.dp));return}
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        TarjetaAdmin {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Text("Pedido #${numeroPedido(p.pedido.id)}",Modifier.weight(1f),fontSize=16.sp,fontWeight=FontWeight.Bold)
                ChipEstadoEstudiante(EstadoPedido.valueOf(p.pedido.estado))
            }
            Text("${fechaPedido(p.pedido.fecha)} · Recojo ${p.pedido.horaRecojo}",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
        TarjetaAdmin {
            Text("Cliente",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Text("${p.cliente.nombre} · Estudiante",fontSize=13.sp,fontWeight=FontWeight.Bold)
            HorizontalDivider(Modifier.padding(vertical=6.dp),color=MaterialTheme.colorScheme.outlineVariant)
            p.detalles.forEach {linea -> Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) {
                Text(linea.nombreProducto,Modifier.weight(1f),fontSize=12.sp)
                Text("x${linea.cantidad}",Modifier.width(32.dp),fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Text(dinero(linea.precioUnitario*linea.cantidad),fontSize=12.sp,fontWeight=FontWeight.SemiBold)
            } }
            Row(Modifier.padding(top=5.dp)) {
                Text("Total",Modifier.weight(1f),fontSize=14.sp,fontWeight=FontWeight.Bold)
                Text(dinero(p.pedido.total),fontSize=14.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
            }
        }
        if(p.pedido.nota.isNotBlank()) Surface(color=MaterialTheme.colorScheme.tertiaryContainer,contentColor=MaterialTheme.colorScheme.onTertiaryContainer,shape=RoundedCornerShape(12.dp)) {
            Column(Modifier.fillMaxWidth().padding(14.dp),verticalArrangement=Arrangement.spacedBy(6.dp)) {
                Text("Nota del cliente",fontSize=11.sp,fontWeight=FontWeight.Bold)
                Text(p.pedido.nota,fontSize=12.sp)
            }
        }
        Text("Cambiar estado",fontSize=14.sp,fontWeight=FontWeight.Bold)
        Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)) {
            EstadoPedido.entries.filter {it!=EstadoPedido.CANCELADO}.forEach {e ->
                FiltroAdmin(etiquetaEstado(e),p.pedido.estado==e.name,{vm.cambiar(p,e)},
                    enabled=p.pedido.estado==e.name || vm.siguiente(p)==e)
            }
        }
        error?.let {Text(it,color=MaterialTheme.colorScheme.error,fontSize=12.sp)}
        Spacer(Modifier.height(10.dp))
        vm.siguiente(p)?.let {e -> BotonAdmin("Pasar a ${etiquetaEstado(e)}",{vm.avanzar(p)},Modifier.fillMaxWidth())}
        if(vm.puedeCancelar(p)) OutlinedButton(onClick={vm.cancelar(p)},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(10.dp),border=BorderStroke(1.dp,MaterialTheme.colorScheme.error)) {
            Text("Cancelar pedido",fontSize=13.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.error)
        }
    }
}
