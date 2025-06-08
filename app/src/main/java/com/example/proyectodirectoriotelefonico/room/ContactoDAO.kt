package com.example.proyectodirectoriotelefonico.room

import androidx.room.*
import com.example.proyectodirectoriotelefonico.model.Contacto
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao {

    @Query("SELECT * FROM contactos")
    fun obtenerContactos(): Flow<List<Contacto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(contacto: Contacto)

    @Update
    suspend fun actualizar(contacto: Contacto)

    @Delete
    suspend fun eliminar(contacto: Contacto)
}