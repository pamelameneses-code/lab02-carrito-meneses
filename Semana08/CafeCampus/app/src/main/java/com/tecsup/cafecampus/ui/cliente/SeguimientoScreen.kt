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

@Composable fun SeguimientoScreen(vm: PedidosClienteViewModel, id: Int) {
    val flujo=remember(vm,id) {vm.pedido(id)}
    val pedido by flujo.collectAsState()
    val p=pedido
    if(p==null) {Text("Cargando pedido…",Modifier.padding(16.dp));return}
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        Surface(color=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary,shape=RoundedCornerShape(12.dp)) {
            Row(Modifier.fillMaxWidth().padding(16.dp),verticalAlignment=Alignment.CenterVertically) {
                Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(5.dp)) {
                    Text("Pedido #${numeroPedido(p.pedido.id)}",fontSize=18.sp,fontWeight=FontWeight.Bold)
                    Text("Recojo: ${p.pedido.horaRecojo}",fontSize=12.sp)
                    Text("Tiempo estimado: 10 – 15 min",fontSize=11.sp)
                }
                Icon(Icons.Default.Schedule,null,Modifier.size(30.dp))
            }
        }
        ProgresoPedidoEstudiante(EstadoPedido.valueOf(p.pedido.estado))
        TarjetaEstudiante {
            Text(p.detalles.joinToString(" · ") {"${it.cantidad} × ${it.nombreProducto}"},fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Total  ${dinero(p.pedido.total)}",fontSize=14.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
        }
    }
}
