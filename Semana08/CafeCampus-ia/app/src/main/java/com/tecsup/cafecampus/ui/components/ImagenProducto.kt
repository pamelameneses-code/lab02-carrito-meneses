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
import com.tecsup.cafecampus.ui.cliente.IlustracionProducto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val cacheFotos = object : LruCache<String,Bitmap>(16*1024) {
    override fun sizeOf(key: String,value: Bitmap): Int = (value.byteCount/1024).coerceAtLeast(1)
}

// Carga visual fuera del hilo principal. Si falta o falla la foto, conserva la ilustración.
@Composable fun ImagenProducto(fotoUri: String?, icono: String, modifier: Modifier = Modifier) {
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
    if(foto==null) IlustracionProducto(icono,modifier)
    else Image(bitmap=foto.asImageBitmap(),contentDescription="Foto del producto",modifier=modifier.clip(RoundedCornerShape(12.dp)),contentScale=ContentScale.Crop)
}
