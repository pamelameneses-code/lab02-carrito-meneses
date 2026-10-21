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

import androidx.compose.foundation.lazy.grid.*

@Composable fun MenuScreen(vm: MenuViewModel, sesion: SesionViewModel, onProducto: (Int) -> Unit, onBolsa: () -> Unit) {
    val productos by vm.filtrados.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val carrito by vm.carrito.collectAsState()
    val usuario by sesion.usuarioActual.collectAsState()
    val abierto by sesion.recibirPedidos.collectAsState()
    Column(Modifier.fillMaxSize().padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Row(Modifier.padding(top=12.dp),verticalAlignment=Alignment.CenterVertically) {
            Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text("¡Hola, ${usuario?.nombre ?: "estudiante"}!",fontSize=23.sp,fontWeight=FontWeight.Bold)
                Text("¿Qué se te antoja hoy?",fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
            BadgedBox(badge={ if(carrito.isNotEmpty()) Badge { Text(carrito.sumOf {it.cantidad}.toString(),fontSize=9.sp) } }) {
                IconButton(onClick=onBolsa) { Icon(Icons.Default.ShoppingBag,"Ver bolsa",Modifier.size(24.dp)) }
            }
        }
        if(!abierto) Text("Cafetería cerrada",color=MaterialTheme.colorScheme.error,fontSize=12.sp)
        OutlinedTextField(
            value=busqueda,onValueChange=vm::buscar,
            placeholder={ Text("Buscar en el menú...",fontSize=12.sp) },
            leadingIcon={ Icon(Icons.Default.Search,null,Modifier.size(18.dp)) },
            trailingIcon={ if(busqueda.isNotEmpty()) IconButton(onClick={vm.buscar("")}) {Icon(Icons.Default.Close,"Borrar búsqueda",Modifier.size(18.dp))} },
            singleLine=true,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),
            textStyle=MaterialTheme.typography.bodySmall,
            colors=OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor=MaterialTheme.colorScheme.surface,
                focusedContainerColor=MaterialTheme.colorScheme.surface,
                unfocusedBorderColor=MaterialTheme.colorScheme.outlineVariant
            )
        )
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)) {
            vm.categorias.forEach { (valor,etiqueta) -> FiltroEstudiante(etiqueta,categoria==valor,{vm.categoria.value=valor}) }
        }
        Text(if(busqueda.isBlank()) "Populares hoy" else "Resultados de búsqueda",fontSize=14.sp,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=4.dp))
        if(busqueda.isNotBlank() && productos.isEmpty()) Text("No se encontraron productos. Prueba otra palabra o la categoría Todo.",fontSize=12.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
        LazyVerticalGrid(columns=GridCells.Fixed(2),modifier=Modifier.weight(1f),
            horizontalArrangement=Arrangement.spacedBy(10.dp),verticalArrangement=Arrangement.spacedBy(10.dp),contentPadding=PaddingValues(bottom=12.dp)) {
            items(productos,key={it.id}) { p -> TarjetaProductoEstudiante(p,{vm.agregar(p,1,"")},{onProducto(p.id)}) }
        }
    }
}
