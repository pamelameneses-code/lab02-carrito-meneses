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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.tecsup.cafecampus.ui.theme.paletaAlimentos
import com.tecsup.cafecampus.ui.theme.colorEstado
import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable fun IlustracionProducto(icono: String, modifier: Modifier = Modifier) {
    val p = paletaAlimentos()
    val fondo = when(icono) { "cafe" -> p.fondoCafe; "pan" -> p.fondoPan; "jugo","botella" -> p.fondoJugo; else -> p.crema }
    Box(modifier.background(fondo,RoundedCornerShape(12.dp)).semantics { contentDescription="Ilustración de $icono" },contentAlignment=Alignment.Center) {
        Canvas(Modifier.fillMaxSize().padding(5.dp)) {
            val s = minOf(size.width,size.height)
            val x = (size.width-s)/2f
            val y = (size.height-s)/2f
            fun punto(a: Float,b: Float) = Offset(x+a*s,y+b*s)
            when(icono) {
                "plato" -> {
                    drawCircle(p.plato,s*0.43f,punto(.5f,.5f))
                    drawCircle(p.crema,s*.31f,punto(.5f,.5f))
                    drawCircle(p.pan,s*.105f,punto(.40f,.46f))
                    drawCircle(p.rojo,s*.08f,punto(.56f,.36f))
                    drawCircle(p.verde,s*.105f,punto(.62f,.57f))
                }
                "cafe" -> {
                    drawOval(p.plato,punto(.12f,.78f),Size(s*.73f,s*.08f))
                    drawCircle(p.plato,s*.14f,punto(.75f,.49f),style=Stroke(s*.045f))
                    drawRoundRect(p.plato,punto(.22f,.27f),Size(s*.49f,s*.53f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.06f))
                    drawRoundRect(p.cafe,punto(.26f,.31f),Size(s*.41f,s*.12f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.04f))
                }
                "pan" -> {
                    drawRoundRect(p.pan,punto(.13f,.25f),Size(s*.76f,s*.2f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.12f))
                    drawRoundRect(p.plato,punto(.13f,.43f),Size(s*.76f,s*.07f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.02f))
                    drawRoundRect(p.verde,punto(.13f,.50f),Size(s*.76f,s*.09f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.02f))
                    drawRoundRect(p.cafe,punto(.13f,.59f),Size(s*.76f,s*.07f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.02f))
                    drawRoundRect(p.pan,punto(.13f,.66f),Size(s*.76f,s*.14f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.08f))
                }
                "jugo","botella" -> {
                    drawLine(p.verde,punto(.62f,.36f),punto(.77f,.06f),s*.045f)
                    val vaso=Path().apply { moveTo(x+s*.25f,y+s*.28f);lineTo(x+s*.76f,y+s*.28f);lineTo(x+s*.67f,y+s*.9f);lineTo(x+s*.35f,y+s*.9f);close() }
                    drawPath(vaso,p.plato)
                    val jugo=Path().apply { moveTo(x+s*.29f,y+s*.5f);lineTo(x+s*.72f,y+s*.5f);lineTo(x+s*.66f,y+s*.87f);lineTo(x+s*.36f,y+s*.87f);close() }
                    drawPath(jugo,p.naranja)
                }
                else -> {
                    drawRoundRect(p.pan,punto(.18f,.3f),Size(s*.65f,s*.47f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(s*.08f))
                    listOf(.32f,.50f,.68f).forEach { drawCircle(p.cafe,s*.03f,punto(it,.49f)) }
                }
            }
        }
    }
}

@Composable fun TarjetaEstudiante(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),color=MaterialTheme.colorScheme.surface,
        border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(12.dp),verticalArrangement=Arrangement.spacedBy(6.dp),content=content)
    }
}

@Composable fun FiltroEstudiante(texto: String, seleccionado: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(onClick=onClick,modifier=modifier,shape=RoundedCornerShape(50),
        color=if(seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor=if(seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        border=if(seleccionado) null else BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Box(Modifier.heightIn(min=34.dp).padding(horizontal=12.dp,vertical=8.dp),contentAlignment=Alignment.Center) {
            Text(texto,fontSize=11.sp,fontWeight=FontWeight.SemiBold,maxLines=1)
        }
    }
}

@Composable fun TarjetaProductoEstudiante(p: Producto, onAgregar: () -> Unit, onDetalle: () -> Unit) {
    Surface(onClick=onDetalle,shape=RoundedCornerShape(12.dp),color=MaterialTheme.colorScheme.surface,
        border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant),modifier=Modifier.fillMaxWidth()) {
        Column(Modifier.padding(6.dp).height(194.dp)) {
            ImagenProducto(p.fotoUri,p.icono,Modifier.fillMaxWidth().height(72.dp))
            Text(p.nombre,modifier=Modifier.padding(horizontal=4.dp,vertical=8.dp),fontSize=12.sp,fontWeight=FontWeight.Bold,maxLines=2,overflow=TextOverflow.Ellipsis)
            Spacer(Modifier.weight(1f))
            if(!p.disponible) Text("Agotado",Modifier.padding(start=4.dp),color=MaterialTheme.colorScheme.error,fontSize=10.sp)
            Row(Modifier.fillMaxWidth().padding(4.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) {
                Text(dinero(p.precio),fontSize=12.sp,fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
                FilledIconButton(onClick=onAgregar,enabled=p.disponible,modifier=Modifier.size(34.dp),shape=RoundedCornerShape(10.dp)) {
                    Icon(Icons.Default.Add,"Agregar ${p.nombre}",Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable fun ChipDisponible(disponible: Boolean) {
    val color = if(disponible) colorEstado(EstadoPedido.LISTO) else MaterialTheme.colorScheme.error
    Surface(color=color.copy(alpha=.12f),contentColor=color,shape=RoundedCornerShape(50)) {
        Text(if(disponible) "Disponible" else "Agotado",Modifier.padding(horizontal=10.dp,vertical=4.dp),fontSize=10.sp,fontWeight=FontWeight.SemiBold)
    }
}

@Composable fun ChipEstadoEstudiante(estado: EstadoPedido) {
    val color = colorEstado(estado)
    Surface(color=color.copy(alpha=.12f),contentColor=color,shape=RoundedCornerShape(50)) {
        Text(etiquetaEstado(estado),Modifier.padding(horizontal=9.dp,vertical=4.dp),fontSize=10.sp,fontWeight=FontWeight.SemiBold)
    }
}

@Composable fun ProgresoPedidoEstudiante(estado: EstadoPedido) {
    if(estado==EstadoPedido.CANCELADO) { ChipEstadoEstudiante(estado); Text("Tu pedido fue cancelado.",fontSize=12.sp); return }
    val pasos=listOf(EstadoPedido.RECIBIDO,EstadoPedido.PREPARANDO,EstadoPedido.LISTO,EstadoPedido.ENTREGADO)
    val actual=pasos.indexOf(estado)
    val descripciones=listOf("Tu pedido llegó a la cocina","Están cocinando tu pedido","Puedes pasar a recogerlo","¡Buen provecho!")
    Column {
        pasos.forEachIndexed { i,paso ->
            val completado=i<actual || estado==EstadoPedido.ENTREGADO
            val vigente=i==actual && !completado
            val color=when { completado -> colorEstado(EstadoPedido.LISTO); vigente -> MaterialTheme.colorScheme.primary; else -> MaterialTheme.colorScheme.outlineVariant }
            Row(Modifier.fillMaxWidth().height(88.dp),horizontalArrangement=Arrangement.spacedBy(14.dp)) {
                Column(horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.width(28.dp)) {
                    Box(Modifier.size(28.dp).background(if(vigente) color.copy(alpha=.15f) else MaterialTheme.colorScheme.background,CircleShape),contentAlignment=Alignment.Center) {
                        Canvas(Modifier.size(22.dp)) {
                            if(completado || vigente) drawCircle(color) else drawCircle(color,style=Stroke(2.dp.toPx()))
                        }
                        if(completado) Icon(Icons.Default.Check,null,Modifier.size(14.dp),tint=MaterialTheme.colorScheme.onPrimary)
                    }
                    if(i<pasos.lastIndex) Box(Modifier.width(2.dp).weight(1f).background(if(i<actual) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant))
                }
                Column(Modifier.padding(top=4.dp),verticalArrangement=Arrangement.spacedBy(6.dp)) {
                    Text(etiquetaEstado(paso),fontSize=13.sp,fontWeight=FontWeight.Bold,color=if(i<=actual) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=.6f))
                    Text(descripciones[i],fontSize=10.sp,color=MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=if(i<=actual) 1f else .6f))
                }
            }
        }
    }
}

@Composable fun CabeceraEstudiante(titulo: String, volver: () -> Unit) {
    Box(Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.statusBars).height(52.dp)) {
        IconButton(onClick=volver,modifier=Modifier.align(Alignment.CenterStart)) { Icon(Icons.Default.ChevronLeft,"Volver",Modifier.size(24.dp)) }
        Text(titulo,Modifier.align(Alignment.Center),fontSize=14.sp,fontWeight=FontWeight.Bold)
    }
}

@Composable fun NavegacionEstudiante(seleccionado: Int, onDestino: (Int) -> Unit) {
    Surface(color=MaterialTheme.colorScheme.surface,shadowElevation=1.dp) {
        Column {
            HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant)
            Row(Modifier.fillMaxWidth().navigationBarsPadding().height(64.dp)) {
                listOf(Icons.Default.Home,Icons.Default.Menu,Icons.Default.PersonOutline).forEachIndexed { i,icono ->
                    val titulo=listOf("Menú","Pedidos","Perfil")[i]
                    val color=if(i==seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=.65f)
                    Column(Modifier.weight(1f).fillMaxHeight().clickable {onDestino(i)}.padding(top=8.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(5.dp)) {
                        Icon(icono,titulo,Modifier.size(22.dp),tint=color)
                        Text(titulo,fontSize=10.sp,color=color,fontWeight=if(i==seleccionado) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }
    }
}

fun fechaPedido(fecha: Long): String {
    val locale=Locale.forLanguageTag("es-PE")
    val hoy=java.util.Calendar.getInstance()
    val dia=java.util.Calendar.getInstance().apply {timeInMillis=fecha}
    fun mismoDia(a: java.util.Calendar,b: java.util.Calendar) =
        a.get(java.util.Calendar.YEAR)==b.get(java.util.Calendar.YEAR) &&
        a.get(java.util.Calendar.DAY_OF_YEAR)==b.get(java.util.Calendar.DAY_OF_YEAR)
    val ayer=(hoy.clone() as java.util.Calendar).apply {add(java.util.Calendar.DAY_OF_YEAR,-1)}
    val hora=SimpleDateFormat("HH:mm",locale).format(Date(fecha))
    return when {
        mismoDia(dia,hoy) -> "Hoy $hora"
        mismoDia(dia,ayer) -> "Ayer $hora"
        else -> SimpleDateFormat("dd/MM HH:mm",locale).format(Date(fecha))
    }
}
fun numeroPedido(id: Int): String = id.toString().padStart(4,'0')
