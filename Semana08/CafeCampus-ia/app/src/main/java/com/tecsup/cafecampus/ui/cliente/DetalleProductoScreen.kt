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

@Composable fun DetalleProductoScreen(vm: MenuViewModel, id: Int, onAgregado: () -> Unit) {
    val productos by vm.productos.collectAsState()
    val producto=productos.find {it.id==id}
    var cantidad by rememberSaveable(id) {mutableStateOf(1)}
    var nota by rememberSaveable(id) {mutableStateOf("")}
    if(producto==null) {Text("Producto no disponible",Modifier.padding(16.dp));return}
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        ImagenProducto(producto.fotoUri,producto.icono,Modifier.fillMaxWidth().height(180.dp))
        Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            Text(producto.nombre,Modifier.weight(1f),fontSize=21.sp,fontWeight=FontWeight.Bold)
            Text(dinero(producto.precio),color=MaterialTheme.colorScheme.primary,fontSize=17.sp,fontWeight=FontWeight.Bold)
        }
        Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(24.dp)) {
            ChipDisponible(producto.disponible)
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(5.dp)) {
                Icon(Icons.Default.Schedule,null,Modifier.size(14.dp),tint=MaterialTheme.colorScheme.onSurfaceVariant)
                Text("10 – 15 min",fontSize=11.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text(producto.descripcion,fontSize=13.sp,lineHeight=20.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) {
            Text("Cantidad",Modifier.weight(1f),fontSize=14.sp,fontWeight=FontWeight.Bold)
            Surface(shape=RoundedCornerShape(50),color=MaterialTheme.colorScheme.surface,border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
                Row(Modifier.padding(4.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)) {
                    FilledTonalIconButton(onClick={if(cantidad>1)cantidad--},enabled=cantidad>1,modifier=Modifier.size(32.dp)) {Icon(Icons.Default.Remove,"Reducir cantidad",Modifier.size(18.dp))}
                    Text(cantidad.toString(),fontSize=14.sp,fontWeight=FontWeight.Bold)
                    FilledIconButton(onClick={cantidad++},modifier=Modifier.size(32.dp)) {Icon(Icons.Default.Add,"Aumentar cantidad",Modifier.size(18.dp))}
                }
            }
        }
        Text("Nota para la cocina",fontSize=14.sp,fontWeight=FontWeight.Bold)
        OutlinedTextField(value=nota,onValueChange={nota=it},placeholder={Text("Por ejemplo: sin ají",fontSize=12.sp)},modifier=Modifier.fillMaxWidth().heightIn(min=76.dp),shape=RoundedCornerShape(12.dp),textStyle=MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(2.dp))
        Surface(color=MaterialTheme.colorScheme.primaryContainer.copy(alpha=.35f),shape=RoundedCornerShape(12.dp)) {
            Column(Modifier.fillMaxWidth().padding(14.dp),verticalArrangement=Arrangement.spacedBy(2.dp)) {
                Text("Total",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Text(dinero(producto.precio*cantidad),fontSize=16.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
            }
        }
        CafeButton("Agregar al pedido",{vm.agregar(producto,cantidad,nota);onAgregado()},Modifier.fillMaxWidth(),producto.disponible)
    }
}
