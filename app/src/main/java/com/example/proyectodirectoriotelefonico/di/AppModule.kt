package com.example.proyectodirectoriotelefonico.di

import android.content.Context
import androidx.room.Room
import com.example.proyectodirectoriotelefonico.repository.ContactoRepository
import com.example.proyectodirectoriotelefonico.room.ContactoDao
import com.example.proyectodirectoriotelefonico.room.ContactoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContactoDatabase(
        @ApplicationContext appContext: Context
    ): ContactoDatabase {
        return Room.databaseBuilder(
            appContext,
            ContactoDatabase::class.java,
            "contacto_database"
        ).build()
    }


    @Provides
    fun provideContactoDao(database: ContactoDatabase): ContactoDao {
        return database.contactoDao()
    }

    @Provides
    fun provideContactoRepository(dao: ContactoDao): ContactoRepository {
        return ContactoRepository(dao)
    }
}
