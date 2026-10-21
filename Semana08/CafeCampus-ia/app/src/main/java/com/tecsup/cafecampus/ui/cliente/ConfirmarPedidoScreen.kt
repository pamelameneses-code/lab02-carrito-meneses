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

@Composable fun ConfirmarPedidoScreen(vm: PedidosClienteViewModel, menu: MenuViewModel, onConfirmado: (Int) -> Unit, onSeguir: () -> Unit) {
    val items by menu.carrito.collectAsState()
    val abierto by vm.recibirPedidos.collectAsState()
    val error by vm.error.collectAsState()
    val enviando by vm.enviando.collectAsState()
    val creado by vm.creado.collectAsState()
    val horas=remember { vm.horasRecojo() }
    var hora by rememberSaveable {mutableStateOf(horas.first())}
    var pago by rememberSaveable {mutableStateOf("CAJA")}
    LaunchedEffect(creado) {creado?.let {menu.vaciar();vm.consumirCreado();onConfirmado(it.toInt())}}
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        TarjetaEstudiante {
            if(items.isEmpty()) Text("Tu bolsa está vacía",fontSize=13.sp)
            items.forEachIndexed { index,item ->
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                    IlustracionProducto(item.producto.icono,Modifier.size(48.dp))
                    Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                        Text(item.producto.nombre,fontSize=12.sp,fontWeight=FontWeight.Bold)
                        Text("x${item.cantidad}",fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                        if(item.nota.isNotBlank()) Text(item.nota,fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(dinero(item.subtotal),fontSize=12.sp,fontWeight=FontWeight.SemiBold)
                }
                if(index<items.lastIndex) Spacer(Modifier.height(4.dp))
            }
        }
        Text("Hora de recojo",fontSize=14.sp,fontWeight=FontWeight.Bold)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)) {
            horas.forEach {h -> FiltroEstudiante(h,hora==h,{hora=h})}
        }
        Text("Forma de pago",fontSize=14.sp,fontWeight=FontWeight.Bold)
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            listOf("CAJA" to "Pagar en caja","YAPE" to "Yape / Plin").forEach { (v,t) ->
                val seleccionado=pago==v
                Surface(onClick={pago=v},modifier=Modifier.weight(1f),shape=RoundedCornerShape(10.dp),
                    color=if(seleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                    border=BorderStroke(1.dp,if(seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)) {
                    Box(Modifier.height(44.dp),contentAlignment=Alignment.Center) {Text(t,fontSize=12.sp,fontWeight=FontWeight.SemiBold,color=if(seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)}
                }
            }
        }
        TarjetaEstudiante {
            Row {Text("Subtotal",Modifier.weight(1f),fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant);Text(dinero(menu.total()),fontSize=12.sp,fontWeight=FontWeight.SemiBold)}
            Row {Text("Servicio",Modifier.weight(1f),fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant);Text(dinero(0.0),fontSize=12.sp,fontWeight=FontWeight.SemiBold)}
            Row {Text("Total",Modifier.weight(1f),fontSize=14.sp,fontWeight=FontWeight.Bold);Text(dinero(menu.total()),fontSize=14.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)}
        }
        if(!abierto) Text("Cafetería cerrada",fontSize=12.sp,color=MaterialTheme.colorScheme.error)
        error?.let {Text(it,fontSize=12.sp,color=MaterialTheme.colorScheme.error)}
        Spacer(Modifier.height(12.dp))
        CafeButton(if(enviando) "Confirmando…" else "Confirmar pedido",{vm.confirmar(items,hora,pago,"")},Modifier.fillMaxWidth(),abierto&&items.isNotEmpty()&&!enviando)
        OutlinedButton(onClick=onSeguir,enabled=!enviando,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(10.dp),border=BorderStroke(1.dp,MaterialTheme.colorScheme.primary)) {Text("Seguir pidiendo",fontWeight=FontWeight.SemiBold)}
    }
}
