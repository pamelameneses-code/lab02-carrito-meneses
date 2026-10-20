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

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.CircleShape
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle

@Composable fun TarjetaAdmin(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),color=MaterialTheme.colorScheme.surface,
        border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(12.dp),verticalArrangement=Arrangement.spacedBy(7.dp),content=content)
    }
}
@Composable fun FiltroAdmin(texto: String, seleccionado: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier,
    enabled: Boolean = true) {
    Surface(onClick=onClick,enabled=enabled,modifier=modifier,shape=RoundedCornerShape(50),
        color=if(seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        border=if(seleccionado) null else BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant),
        contentColor=if(seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant) {
        Box(Modifier.heightIn(min=32.dp).padding(horizontal=12.dp,vertical=8.dp),contentAlignment=Alignment.Center) {
            Text(texto,fontSize=10.sp,fontWeight=FontWeight.SemiBold,maxLines=1)
        }
    }
}
@Composable fun BotonAdmin(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, peligro: Boolean = false,
    compacto: Boolean = false, enabled: Boolean = true) {
    Button(onClick=onClick,modifier=modifier.heightIn(min=if(compacto) 32.dp else 48.dp),enabled=enabled,
        shape=if(compacto) RoundedCornerShape(50) else RoundedCornerShape(10.dp),contentPadding=PaddingValues(horizontal=14.dp,vertical=if(compacto) 7.dp else 12.dp),
        colors=if(peligro) ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.errorContainer,contentColor=MaterialTheme.colorScheme.error) else ButtonDefaults.buttonColors()) {
        Text(texto,fontSize=if(compacto) 10.sp else 13.sp,fontWeight=FontWeight.SemiBold,maxLines=1)
    }
}
@Composable fun CabeceraAdmin(titulo: String, volver: () -> Unit) {
    Box(Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.statusBars).height(52.dp)) {
        IconButton(onClick=volver,modifier=Modifier.align(Alignment.CenterStart)) {Icon(Icons.Default.ChevronLeft,"Volver")}
        Text(titulo,Modifier.align(Alignment.Center),fontSize=14.sp,fontWeight=FontWeight.Bold)
    }
}
@Composable fun NavegacionAdmin(seleccionado: Int, onDestino: (Int) -> Unit) {
    val iconos=listOf(Icons.Default.BarChart,Icons.Default.Menu,Icons.Default.LocalCafe,Icons.Default.Settings)
    val titulos=listOf("Panel","Pedidos","Menú","Ajustes")
    Surface(color=MaterialTheme.colorScheme.surface) {
        Column {
            HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant)
            Row(Modifier.fillMaxWidth().navigationBarsPadding().height(64.dp)) {
                iconos.forEachIndexed {i,icono ->
                    val color=if(i==seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=.65f)
                    Column(Modifier.weight(1f).fillMaxHeight().clickable {onDestino(i)}.padding(top=9.dp),
                        horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(5.dp)) {
                        Icon(icono,titulos[i],Modifier.size(22.dp),tint=color)
                        Text(titulos[i],fontSize=10.sp,color=color,fontWeight=if(i==seleccionado) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }
    }
}
fun resumenAdmin(p: PedidoConDetalle): String = p.detalles.joinToString(" + ") {"${it.cantidad} × ${it.nombreProducto}"}

@Composable fun AvatarAdmin(modifier: Modifier = Modifier) {
    Box(modifier.background(MaterialTheme.colorScheme.primaryContainer,CircleShape),contentAlignment=Alignment.Center) {
        Icon(Icons.Default.PersonOutline,null,Modifier.fillMaxSize(.55f),tint=MaterialTheme.colorScheme.primary)
    }
}
@Composable fun TarjetaPedidoAdmin(p: PedidoConDetalle, siguiente: EstadoPedido?, puedeCancelar: Boolean,
    onDetalle: () -> Unit, onAvanzar: () -> Unit, onCancelar: () -> Unit) {
    TarjetaAdmin(Modifier.clickable(onClick=onDetalle)) {
        Row(horizontalArrangement=Arrangement.spacedBy(9.dp),verticalAlignment=Alignment.CenterVertically) {
            AvatarAdmin(Modifier.size(36.dp))
            Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text("#${numeroPedido(p.pedido.id)}  ${p.cliente.nombre}",fontSize=12.sp,fontWeight=FontWeight.Bold,maxLines=1,overflow=TextOverflow.Ellipsis)
                Text(resumenAdmin(p),fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant,maxLines=1,overflow=TextOverflow.Ellipsis)
            }
            ChipEstadoEstudiante(EstadoPedido.valueOf(p.pedido.estado))
        }
        Text("Recojo ${p.pedido.horaRecojo}  ·  ${dinero(p.pedido.total)}"+
            if(p.pedido.nota.isNotBlank()) "  ·  Nota: ${p.pedido.nota}" else "",
            fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant,maxLines=2,overflow=TextOverflow.Ellipsis)
        if(siguiente!=null || puedeCancelar) Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            siguiente?.let { BotonAdmin("Pasar a ${etiquetaEstado(it)}",onAvanzar,Modifier.weight(1f),compacto=true) }
            if(puedeCancelar) BotonAdmin("Cancelar",onCancelar,Modifier.weight(1f),peligro=true,compacto=true)
        }
    }
}
