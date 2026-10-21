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

import com.tecsup.cafecampus.data.local.entity.Producto
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
@Composable fun FormularioProductoScreen(vm: MenuAdminViewModel, id: Int?, onGuardado: () -> Unit, onCancelar: () -> Unit = onGuardado) {
    val productos by vm.productos.collectAsState()
    val producto=productos.find {it.id==id}
    val error by vm.error.collectAsState()
    val guardado by vm.guardado.collectAsState()
    val guardando by vm.guardando.collectAsState()
    val importandoFoto by vm.importandoFoto.collectAsState()
    val fotoImportada by vm.fotoImportada.collectAsState()
    var fotoUri by rememberSaveable(id) {mutableStateOf<String?>(null)}
    val galeria=rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {uri ->
        uri?.let {vm.importarFoto(it.toString())}
    }
    LaunchedEffect(fotoImportada) {fotoImportada?.let {fotoUri=it;vm.consumirFoto()}}
    var nombre by rememberSaveable(id) {mutableStateOf("")}
    var categoria by rememberSaveable(id) {mutableStateOf("Snacks")}
    var precio by rememberSaveable(id) {mutableStateOf("")}
    var descripcion by rememberSaveable(id) {mutableStateOf("")}
    var disponible by rememberSaveable(id) {mutableStateOf(true)}
    var icono by rememberSaveable(id) {mutableStateOf("pan")}
    var inicializado by rememberSaveable(id) {mutableStateOf(false)}
    var elegirIcono by remember {mutableStateOf(false)}
    var elegirCategoria by remember {mutableStateOf(false)}
    LaunchedEffect(producto) {
        if(!inicializado && producto!=null) {
            nombre=producto.nombre;categoria=producto.categoria;precio=producto.precio.toString();descripcion=producto.descripcion
            disponible=producto.disponible;icono=producto.icono;fotoUri=producto.fotoUri;inicializado=true
        }
    }
    LaunchedEffect(guardado) {if(guardado) {vm.consumirGuardado();onGuardado()}}
    DisposableEffect(Unit) {onDispose {vm.consumirGuardado();vm.cancelarImportacionFoto()}}
    if(id!=null && producto==null) {Text("Cargando producto…",Modifier.padding(16.dp));return}
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).imePadding().padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Surface(onClick={if(!importandoFoto) galeria.launch(arrayOf("image/*"))},color=MaterialTheme.colorScheme.primaryContainer.copy(alpha=.45f),shape=RoundedCornerShape(14.dp)) {
            Row(Modifier.fillMaxWidth().padding(12.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)) {
                ImagenProducto(fotoUri,icono,Modifier.size(58.dp),nombre=nombre)
                Column(verticalArrangement=Arrangement.spacedBy(4.dp)) {
                    Text("Imagen del producto",fontSize=12.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
                    Text(if(importandoFoto) "Cargando foto…" else "Toca para elegir una foto de tu galería",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            TextButton(onClick={fotoUri=FOTO_SOLO_ILUSTRACION;elegirIcono=true},enabled=!importandoFoto) {Text("Usar ilustración",fontSize=11.sp)}
            if(fotoUri!=null) TextButton(onClick={fotoUri=null},enabled=!importandoFoto) {Text(if(fotoUri==FOTO_SOLO_ILUSTRACION) "Restaurar foto incluida" else "Quitar foto",fontSize=11.sp)}
        }
        CampoProductoAdmin(nombre,{nombre=it},"Nombre del producto")
        Box {
            OutlinedTextField(value=categoria,onValueChange={},readOnly=true,label={Text("Categoría",fontSize=10.sp)},modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),textStyle=MaterialTheme.typography.bodySmall,
                trailingIcon={IconButton(onClick={elegirCategoria=true}) {Icon(Icons.Default.ChevronRight,"Elegir categoría")}})
            DropdownMenu(expanded=elegirCategoria,onDismissRequest={elegirCategoria=false}) {
                (listOf("Snacks","Menús","Bebidas","Desayunos","Almuerzos")+productos.map {it.categoria}).distinct().forEach {c ->
                    DropdownMenuItem(text={Text(c)},onClick={categoria=c;elegirCategoria=false})
                }
            }
        }
        CampoProductoAdmin(precio,{precio=it},"Precio (S/)",decimal=true)
        CampoProductoAdmin(descripcion,{descripcion=it},"Descripción",multilinea=true)
        TarjetaAdmin {Row(verticalAlignment=Alignment.CenterVertically) {
            Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                Text("Disponible en el menú",fontSize=12.sp,fontWeight=FontWeight.Bold)
                Text("Los estudiantes podrán pedirlo",fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked=disponible,onCheckedChange={disponible=it})
        }}
        error?.let {Text(it,fontSize=12.sp,color=MaterialTheme.colorScheme.error)}
        Spacer(Modifier.height(12.dp))
        BotonAdmin(if(guardando) "Guardando…" else "Guardar producto",{
            vm.guardar(Producto(id=id ?: 0,nombre=nombre,descripcion=descripcion,categoria=categoria,precio=precio.replace(',','.').toDoubleOrNull() ?: 0.0,disponible=disponible,icono=icono,fotoUri=fotoUri))
        },Modifier.fillMaxWidth(),enabled=!guardando && !importandoFoto)
        OutlinedButton(onClick=onCancelar,enabled=!guardando,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(10.dp),border=BorderStroke(1.dp,MaterialTheme.colorScheme.primary)) {Text("Cancelar",fontSize=13.sp,fontWeight=FontWeight.Bold)}
    }
    if(elegirIcono) AlertDialog(onDismissRequest={elegirIcono=false},title={Text("Imagen del producto")},text={
        Column(verticalArrangement=Arrangement.spacedBy(10.dp)) {
            listOf(listOf("cafe","plato","jugo"),listOf("pan","botella","dulce")).forEach {fila ->
                Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {fila.forEach {i ->
                    Column(Modifier.weight(1f).clickable {icono=i;elegirIcono=false},horizontalAlignment=Alignment.CenterHorizontally) {
                        IlustracionProducto(i,Modifier.size(62.dp));Text(i,fontSize=10.sp)
                    }
                }}
            }
        }
    },confirmButton={TextButton(onClick={elegirIcono=false}) {Text("Cerrar")}})
}

@Composable private fun CampoProductoAdmin(valor: String, onChange: (String) -> Unit, etiqueta: String, multilinea: Boolean = false, decimal: Boolean = false) {
    OutlinedTextField(value=valor,onValueChange=onChange,label={Text(etiqueta,fontSize=10.sp)},modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(12.dp),singleLine=!multilinea,minLines=if(multilinea) 2 else 1,
        textStyle=MaterialTheme.typography.bodySmall,keyboardOptions=KeyboardOptions(keyboardType=if(decimal) KeyboardType.Decimal else KeyboardType.Text),
        colors=OutlinedTextFieldDefaults.colors(unfocusedContainerColor=MaterialTheme.colorScheme.surface,focusedContainerColor=MaterialTheme.colorScheme.surface,
            unfocusedBorderColor=MaterialTheme.colorScheme.outlineVariant))
}
