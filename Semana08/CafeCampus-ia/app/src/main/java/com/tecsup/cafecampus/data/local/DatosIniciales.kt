package com.tecsup.cafecampus.data.local

import androidx.room.withTransaction
import com.tecsup.cafecampus.data.local.entity.*
import com.tecsup.cafecampus.model.Rol
object DatosIniciales {
    suspend fun sembrar(db: CafeDatabase) = db.withTransaction {
        if (db.usuarioDao().contar() == 0) {
            listOf(
                Producto(nombre="Café americano", descripcion="Café recién preparado", categoria="Bebidas", precio=4.0, icono="cafe"),
                Producto(nombre="Sándwich de pollo", descripcion="Pollo y verduras frescas", categoria="Snacks", precio=9.0, icono="pan"),
                Producto(nombre="Menú del día", descripcion="Almuerzo del campus", categoria="Menús", precio=12.0, icono="plato"),
                Producto(nombre="Jugo de naranja", descripcion="Naranja natural", categoria="Bebidas", precio=5.0, icono="jugo"),
                Producto(nombre="Empanada", descripcion="Horneada del día", categoria="Snacks", precio=4.5, icono="pan"),
                Producto(nombre="Chicha morada", descripcion="Bebida de maíz morado", categoria="Bebidas", precio=3.5, icono="botella")
            ).forEach { db.productoDao().insertar(it) }
            db.usuarioDao().insertar(Usuario(nombre="Juan", correo="juan@campus.test", clave="1234", rol=Rol.CLIENTE.name))
            db.usuarioDao().insertar(Usuario(nombre="Administrador", correo="admin@cafe.test", clave="admin", rol=Rol.ADMIN.name))
        }
    }
}
