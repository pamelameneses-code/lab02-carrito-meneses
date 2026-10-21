package com.tecsup.cafecampus.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.LruCache
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.tecsup.cafecampus.R
import java.text.Normalizer
import java.util.Locale
import com.tecsup.cafecampus.ui.cliente.IlustracionProducto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val cacheFotos = object : LruCache<String,Bitmap>(16*1024) {
    override fun sizeOf(key: String,value: Bitmap): Int = (value.byteCount/1024).coerceAtLeast(1)
}

// Elección explícita de dibujo, diferenciada de null (foto incluida por defecto).
const val FOTO_SOLO_ILUSTRACION = "ilustracion://producto"

private fun fotoIncluida(nombre: String): Int? {
    val clave=Normalizer.normalize(nombre.trim(),Normalizer.Form.NFD)
        .filterNot {Character.getType(it)==Character.NON_SPACING_MARK.toInt()}
        .lowercase(Locale.ROOT).replace(Regex("\\s+")," ")
    return when(clave) {
        "empanada" -> R.drawable.foto_empanada
        "chicha morada" -> R.drawable.foto_chicha_morada
        "sandwich de pollo" -> R.drawable.foto_sandwich_pollo
        "jugo de naranja" -> R.drawable.foto_jugo_naranja
        "cafe americano" -> R.drawable.foto_cafe_americano
        "menu del dia" -> R.drawable.foto_menu_dia
        else -> null
    }
}

// Foto de galería > foto incluida por nombre > ilustración. No consulta ni modifica Room.
@Composable fun ImagenProducto(fotoUri: String?, icono: String, modifier: Modifier = Modifier, nombre: String = "") {
    val bitmap by produceState<Bitmap?>(null,fotoUri) {
        value=null
        if(fotoUri!=null) value=withContext(Dispatchers.IO) {
            cacheFotos.get(fotoUri) ?: try {
                val uri=Uri.parse(fotoUri)
                if(uri.scheme!="file") null else uri.path?.let {ruta ->
                    BitmapFactory.decodeFile(ruta)?.also {cacheFotos.put(fotoUri,it)}
                }
            } catch(_: Exception) {null}
        }
    }
    val foto=bitmap
    val recurso=if(fotoUri==FOTO_SOLO_ILUSTRACION) null else fotoIncluida(nombre)
    when {
        foto!=null -> Image(bitmap=foto.asImageBitmap(),contentDescription="Foto de $nombre",modifier=modifier.clip(RoundedCornerShape(12.dp)),contentScale=ContentScale.Crop)
        recurso!=null -> Image(painter=painterResource(recurso),contentDescription="Foto de $nombre",modifier=modifier.clip(RoundedCornerShape(12.dp)),contentScale=ContentScale.Crop)
        else -> IlustracionProducto(icono,modifier)
    }
}
