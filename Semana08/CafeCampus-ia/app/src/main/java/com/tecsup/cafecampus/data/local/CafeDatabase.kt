package com.tecsup.cafecampus.data.local

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.data.local.dao.*
@Database(entities = [Producto::class, Usuario::class, Pedido::class, DetallePedido::class], version = 2, exportSchema = false)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    companion object {
        // Migración aditiva: mantiene productos, usuarios y pedidos existentes.
        val MIGRACION_1_2 = object : Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE productos ADD COLUMN fotoUri TEXT")
            }
        }
        @Volatile private var INSTANCE: CafeDatabase? = null
        fun obtener(context: Context): CafeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, CafeDatabase::class.java,
                "cafecampus.db").addMigrations(MIGRACION_1_2).build().also { INSTANCE = it }
        }
    }
}
