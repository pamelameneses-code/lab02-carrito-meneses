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
import com.tecsup.cafecampus.data.local.entity.Producto
@Composable fun MenuAdminScreen(vm: MenuAdminViewModel, onEditar: (Int?) -> Unit) {
    val productos by vm.filtrados.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val error by vm.error.collectAsState()
    var eliminar by remember {mutableStateOf<Producto?>(null)}
    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(10.dp),contentPadding=PaddingValues(bottom=92.dp)) {
            item {Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)) {
                vm.categorias.forEach {(valor,titulo) -> FiltroAdmin(titulo,categoria==valor,{vm.categoria.value=valor})}
            };error?.let {Text(it,color=MaterialTheme.colorScheme.error,fontSize=12.sp)}}
            items(productos,key={it.id}) {p -> TarjetaAdmin {
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(9.dp)) {
                    ImagenProducto(p.fotoUri,p.icono,Modifier.size(64.dp),nombre=p.nombre)
                    Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(5.dp)) {
                        Text(p.nombre,fontSize=12.sp,fontWeight=FontWeight.Bold,maxLines=2)
                        Text("${vm.etiquetaCategoria(p.categoria)} · ${dinero(p.precio)}",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                        Box(Modifier.clickable {vm.alternarDisponibilidad(p)}) {ChipDisponible(p.disponible)}
                    }
                    IconButton(onClick={onEditar(p.id)},modifier=Modifier.size(30.dp)) {Icon(Icons.Default.Edit,"Editar ${p.nombre}",Modifier.size(18.dp),tint=MaterialTheme.colorScheme.primary)}
                    IconButton(onClick={eliminar=p},modifier=Modifier.size(30.dp)) {Icon(Icons.Default.Delete,"Eliminar ${p.nombre}",Modifier.size(18.dp),tint=MaterialTheme.colorScheme.error)}
                }
            } }
        }
        FloatingActionButton(onClick={onEditar(null)},modifier=Modifier.align(Alignment.BottomEnd).padding(16.dp),shape=RoundedCornerShape(20.dp),containerColor=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary) {
            Icon(Icons.Default.Add,"Agregar producto",Modifier.size(30.dp))
        }
    }
    eliminar?.let {p -> AlertDialog(onDismissRequest={eliminar=null},title={Text("Eliminar producto")},text={Text("¿Eliminar ${p.nombre}? Los pedidos antiguos conservarán sus datos.")},
        confirmButton={TextButton(onClick={vm.eliminar(p);eliminar=null}) {Text("Eliminar")}},dismissButton={TextButton(onClick={eliminar=null}) {Text("Cancelar")}})}
}
