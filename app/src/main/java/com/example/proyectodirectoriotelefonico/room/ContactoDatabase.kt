package com.example.proyectodirectoriotelefonico.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectodirectoriotelefonico.model.Contacto

@Database(entities = [Contacto::class], version = 1, exportSchema = false)
abstract class ContactoDatabase : RoomDatabase() {
    abstract fun contactoDao(): ContactoDao
}