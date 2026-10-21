package com.tecsup.cafecampus.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.File
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

// Usa únicamente applicationContext. Copia la selección al espacio privado de la app.
class AlmacenFotos(context: Context) {
    private val app = context.applicationContext
    suspend fun importar(origen: String): String = withContext(Dispatchers.IO) {
        val uri=Uri.parse(origen)
        require(uri.scheme=="content") {"Selecciona una imagen desde la galería"}
        val carpeta=File(app.filesDir,"fotos_productos").apply {check(isDirectory || mkdirs())}
        val temporal=File.createTempFile("foto_",".tmp",app.cacheDir)
        val destino=File(carpeta,"${UUID.randomUUID()}.jpg")
        var bitmap: Bitmap? = null
        var girado: Bitmap? = null
        try {
            app.contentResolver.openInputStream(uri).use {entrada ->
                requireNotNull(entrada) {"No se pudo abrir la foto"}
                temporal.outputStream().use {salida ->
                    val buffer=ByteArray(8192);var total=0L
                    while(true) {
                        ensureActive()
                        val n=entrada.read(buffer);if(n<0) break
                        total+=n;require(total<=20L*1024*1024) {"La foto supera 20 MB"}
                        salida.write(buffer,0,n)
                    }
                }
            }
            val limites=BitmapFactory.Options().apply {inJustDecodeBounds=true}
            BitmapFactory.decodeFile(temporal.absolutePath,limites)
            require(limites.outWidth>0 && limites.outHeight>0) {"El archivo no es una imagen compatible"}
            var muestra=1
            while(maxOf(limites.outWidth/muestra,limites.outHeight/muestra)>1600) muestra*=2
            val original=BitmapFactory.decodeFile(temporal.absolutePath,BitmapFactory.Options().apply {inSampleSize=muestra})
                ?: error("No se pudo leer la foto")
            bitmap=original
            val orientacion=try {ExifInterface(temporal.absolutePath).getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)} catch(_: Exception) {ExifInterface.ORIENTATION_NORMAL}
            val matriz=Matrix().apply {
                when(orientacion) {
                    ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> setScale(-1f,1f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> setRotate(180f)
                    ExifInterface.ORIENTATION_FLIP_VERTICAL -> setScale(1f,-1f)
                    ExifInterface.ORIENTATION_TRANSPOSE -> {setRotate(90f);postScale(-1f,1f)}
                    ExifInterface.ORIENTATION_ROTATE_90 -> setRotate(90f)
                    ExifInterface.ORIENTATION_TRANSVERSE -> {setRotate(-90f);postScale(-1f,1f)}
                    ExifInterface.ORIENTATION_ROTATE_270 -> setRotate(-90f)
                }
            }
            val resultado=if(matriz.isIdentity) original else Bitmap.createBitmap(original,0,0,original.width,original.height,matriz,true)
            girado=resultado
            ensureActive()
            destino.outputStream().use {check(resultado.compress(Bitmap.CompressFormat.JPEG,88,it))}
            ensureActive()
            Uri.fromFile(destino).toString()
        } catch(e: Throwable) {
            destino.delete()
            throw e
        } finally {
            if(girado!==bitmap) girado?.recycle()
            bitmap?.recycle()
            temporal.delete()
        }
    }
}
