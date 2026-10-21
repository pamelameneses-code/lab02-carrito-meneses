package com.tecsup.cafecampus.ui.components

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
import androidx.compose.ui.unit.dp
import com.tecsup.cafecampus.model.*
import com.tecsup.cafecampus.ui.components.*

import com.tecsup.cafecampus.data.local.entity.Producto
import java.util.Locale
fun dinero(valor: Double): String = String.format(Locale.forLanguageTag("es-PE"),"S/ %.2f",valor)
@Composable fun CafeCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    OutlinedCard(modifier=modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),
        colors=CardDefaults.outlinedCardColors(containerColor=MaterialTheme.colorScheme.surface),
        border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(8.dp),content=content)
    }
}
@Composable fun ProductoCard(p: Producto, onAgregar: () -> Unit = {}, onClick: () -> Unit) {
    CafeCard(Modifier.clickable(onClick=onClick)) {
        ImagenProducto(p.fotoUri,p.icono,Modifier.fillMaxWidth().height(90.dp))
        Text(p.nombre,style=MaterialTheme.typography.titleSmall,minLines=2)
        Text(p.categoria,style=MaterialTheme.typography.bodySmall)
        Row(verticalAlignment=Alignment.CenterVertically) {
            Text(dinero(p.precio),Modifier.weight(1f),color=MaterialTheme.colorScheme.primary)
            FilledIconButton(onClick=onAgregar,enabled=p.disponible) { Icon(Icons.Default.Add,"Agregar ${p.nombre}") }
        }
        if(!p.disponible) Text("Agotado",color=MaterialTheme.colorScheme.error)
    }
}
