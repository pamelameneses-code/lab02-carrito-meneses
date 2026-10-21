package com.tecsup.cafecampus.data.local

import android.content.Context
import androidx.room.*
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.dao.*
@Database(entities = [Producto::class, Usuario::class, Pedido::class, DetallePedido::class], version = 1, exportSchema = false)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    companion object {
        @Volatile private var INSTANCE: CafeDatabase? = null
        fun obtener(context: Context): CafeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, CafeDatabase::class.java,
                "cafecampus.db").build().also { INSTANCE = it }
        }
    }
}
