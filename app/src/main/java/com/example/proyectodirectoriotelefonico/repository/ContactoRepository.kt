package com.example.proyectodirectoriotelefonico.repository

import com.example.proyectodirectoriotelefonico.model.Contacto
import com.example.proyectodirectoriotelefonico.room.ContactoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactoRepository @Inject constructor(
    private val contactoDao: ContactoDao
) {
    fun obtenerContactos(): Flow<List<Contacto>> = contactoDao.obtenerContactos()

    suspend fun insertar(contacto: Contacto) = contactoDao.insertar(contacto)

    suspend fun actualizar(contacto: Contacto) = contactoDao.actualizar(contacto)

    suspend fun eliminar(contacto: Contacto) = contactoDao.eliminar(contacto)
}