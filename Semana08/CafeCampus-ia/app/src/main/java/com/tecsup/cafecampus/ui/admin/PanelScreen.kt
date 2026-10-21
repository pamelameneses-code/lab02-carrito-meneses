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
import androidx.compose.ui.platform.LocalDensity

@Composable fun PanelScreen(vm: PanelAdminViewModel, onPedido: (Int) -> Unit, onVerTodos: () -> Unit = {}, notificacionesActivas: Boolean = true) {
    val horas by vm.pedidosPorHora.collectAsState()
    val desplazamiento = rememberScrollState()
    val densidad = LocalDensity.current
    LaunchedEffect(desplazamiento.maxValue) {
        val destino=with(densidad) {((vm.horaActual-3).coerceAtLeast(0)*48).dp.roundToPx()}
        desplazamiento.scrollTo(destino.coerceAtMost(desplazamiento.maxValue))
    }
    val nuevos by vm.nuevos.collectAsState()
    val preparando by vm.preparando.collectAsState()
    val listos by vm.listos.collectAsState()
    val ventas by vm.ventasHoy.collectAsState()
    val cantidad by vm.cantidadHoy.collectAsState()
    val pedidos by vm.pedidosNuevos.collectAsState()
    val error by vm.error.collectAsState()
    LazyColumn(Modifier.fillMaxSize().padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(14.dp),contentPadding=PaddingValues(top=12.dp,bottom=16.dp)) {
        item { Row(verticalAlignment=Alignment.CenterVertically) {
            Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text("Panel de control",fontSize=23.sp,fontWeight=FontWeight.Bold)
                Text("Cafetería · Hoy",fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick=onVerTodos) {
                BadgedBox(badge={
                    if(notificacionesActivas && nuevos>0) Badge {
                        Text(if(nuevos>99) "99+" else nuevos.toString())
                    }
                }) {
                    Icon(
                        if(notificacionesActivas) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                        contentDescription=if(notificacionesActivas) "$nuevos pedidos nuevos. Ver pedidos" else "Notificaciones desactivadas. Ver pedidos",
                        modifier=Modifier.size(24.dp),
                        tint=if(notificacionesActivas && nuevos>0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } }
        item { Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            listOf(Triple("Nuevos",nuevos,EstadoPedido.RECIBIDO),Triple("Preparando",preparando,EstadoPedido.PREPARANDO),Triple("Listos",listos,EstadoPedido.LISTO)).forEach { (titulo,n,estado) ->
                val color=colorEstado(estado)
                Surface(modifier=Modifier.weight(1f),shape=RoundedCornerShape(16.dp),color=color.copy(alpha=.12f)) {
                    Column(Modifier.padding(vertical=16.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(7.dp)) {
                        Text(n.toString(),fontSize=26.sp,fontWeight=FontWeight.Bold,color=color)
                        Text(titulo,fontSize=10.sp,fontWeight=FontWeight.SemiBold,color=color)
                    }
                }
            }
        } }
        item { Surface(color=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary,shape=RoundedCornerShape(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(16.dp),verticalArrangement=Arrangement.spacedBy(5.dp)) {
                Text("Ventas del día",fontSize=12.sp)
                Row(verticalAlignment=Alignment.Bottom) {
                    Text(dinero(ventas),Modifier.weight(1f),fontSize=24.sp,fontWeight=FontWeight.Bold)
                    Text("$cantidad pedidos",fontSize=11.sp,fontWeight=FontWeight.SemiBold)
                }
            }
        } }
        item { Text("Pedidos por hora",fontSize=14.sp,fontWeight=FontWeight.Bold) }
        item { TarjetaAdmin {
            val maximo=(horas.maxOfOrNull {it.cantidad} ?: 0).coerceAtLeast(1)
            Text("Hoy · pedidos no cancelados",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            if(horas.all {it.cantidad==0}) Text("Todavía no hay pedidos hoy",fontSize=12.sp)
            Row(Modifier.fillMaxWidth().horizontalScroll(desplazamiento).height(130.dp),verticalAlignment=Alignment.Bottom) {
                horas.forEach {punto -> Column(Modifier.width(48.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(4.dp)) {
                    Text(punto.cantidad.toString(),fontSize=10.sp)
                    Box(Modifier.width(28.dp).height(if(punto.cantidad==0) 2.dp else (punto.cantidad.toFloat()/maximo*86).dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha=if(punto.cantidad==maximo) 1f else .5f),RoundedCornerShape(5.dp)))
                    Text("${punto.hora}h",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                } }
            }
            Text("Desliza para ver las 24 horas",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        } }
        item { Row(verticalAlignment=Alignment.CenterVertically) {
            Text("Nuevos pedidos",Modifier.weight(1f),fontSize=14.sp,fontWeight=FontWeight.Bold)
            TextButton(onClick=onVerTodos,contentPadding=PaddingValues(0.dp)) {Text("Ver todos",fontSize=11.sp,fontWeight=FontWeight.Bold)}
        };error?.let {Text(it,fontSize=12.sp,color=MaterialTheme.colorScheme.error)}
            if(pedidos.isEmpty()) Text("No hay pedidos nuevos",fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(pedidos.take(3),key={it.pedido.id}) {p -> TarjetaAdmin(Modifier.clickable {onPedido(p.pedido.id)}) {
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(5.dp)) {
                    Text("#${numeroPedido(p.pedido.id)}  ${p.cliente.nombre}",fontSize=12.sp,fontWeight=FontWeight.Bold)
                    Text("${resumenAdmin(p)} · ${p.pedido.horaRecojo}",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant,maxLines=1,overflow=TextOverflow.Ellipsis)
                }
                BotonAdmin("Preparar",{vm.preparar(p)},compacto=true)
            }
        } }
    }
}
