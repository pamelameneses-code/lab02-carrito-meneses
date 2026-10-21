package com.tecsup.cafecampus.data.local.dao

import androidx.room.*
import com.tecsup.cafecampus.data.local.entity.Usuario
@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): Usuario?
    @Insert suspend fun insertar(u: Usuario): Long
    @Query("SELECT COUNT(*) FROM usuarios") suspend fun contar(): Int
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1") suspend fun buscar(id: Int): Usuario?
    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY id LIMIT 1") suspend fun porRol(rol: String): Usuario?
}
