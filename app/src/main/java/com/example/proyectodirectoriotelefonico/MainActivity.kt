package com.example.proyectodirectoriotelefonico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.navigation.NavManager
import com.example.proyectodirectoriotelefonico.ui.theme.ProyectoDirectorioTelefonicoTheme
import com.example.proyectodirectoriotelefonico.viewModels.ContactoViewModel
import com.example.proyectodirectoriotelefonico.view.principalScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val contactoViewModel: ContactoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoDirectorioTelefonicoTheme {
                NavManager()
            }
        }
    }
}