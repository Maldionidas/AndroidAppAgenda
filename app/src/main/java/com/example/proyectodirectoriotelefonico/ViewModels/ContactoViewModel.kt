package com.example.proyectodirectoriotelefonico.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectodirectoriotelefonico.model.Contacto
import com.example.proyectodirectoriotelefonico.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ContactoViewModel @Inject constructor(
    private val repository: ContactoRepository
) : ViewModel() {

    open val contactos: StateFlow<List<Contacto>> = repository.obtenerContactos()
        .map { it.sortedBy { contacto -> contacto.nombres } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarContacto(contacto: Contacto) = viewModelScope.launch {
        repository.insertar(contacto)
    }

    fun actualizarContacto(contacto: Contacto) = viewModelScope.launch {
        repository.actualizar(contacto)
    }

    fun eliminarContacto(contacto: Contacto) = viewModelScope.launch {
        repository.eliminar(contacto)
    }
}
