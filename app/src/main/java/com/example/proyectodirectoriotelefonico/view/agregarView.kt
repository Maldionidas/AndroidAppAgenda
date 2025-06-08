package com.example.proyectodirectoriotelefonico.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectodirectoriotelefonico.model.Contacto
import coil.compose.AsyncImage

@Composable
fun AgregarContactoDialog(
    onDismiss: () -> Unit,
    onGuardar: (Contacto) -> Unit,
    contactoExistente: Contacto? = null
) {
    var nombres by remember { mutableStateOf(contactoExistente?.nombres ?: "") }
    var apellidoPaterno by remember { mutableStateOf(contactoExistente?.apellidoPaterno ?: "") }
    var apellidoMaterno by remember { mutableStateOf(contactoExistente?.apellidoMaterno ?: "") }
    var telefono by remember { mutableStateOf(contactoExistente?.telefono ?: "") }
    var correo by remember { mutableStateOf(contactoExistente?.correo ?: "") }
    var direccion by remember { mutableStateOf(contactoExistente?.direccion ?: "") }
    var imagenUri by remember { mutableStateOf<Uri?>(contactoExistente?.fotoUri?.let { Uri.parse(it) }) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (nombres.isNotBlank() && telefono.isNotBlank()) {
                        onGuardar(
                            Contacto(
                                id = contactoExistente?.id ?: 0,
                                nombres = nombres,
                                apellidoPaterno = apellidoPaterno,
                                apellidoMaterno = apellidoMaterno,
                                telefono = telefono,
                                correo = correo,
                                direccion = direccion,
                                fotoUri = imagenUri?.toString()
                            )
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text(if (contactoExistente != null) "Editar Contacto" else "Nuevo Contacto") },
        text = {
            val scrollState = rememberScrollState()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
                .heightIn(max = 350.dp) // altura máxima del dialogo
                .verticalScroll(scrollState)) {

                Button(onClick = { launcher.launch("image/*") }) {
                    Text(text = if (imagenUri != null) "Cambiar imagen" else "Seleccionar imagen")
                }

                imagenUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Foto seleccionada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }

                OutlinedTextField(value = nombres,
                    onValueChange = { nombres = it },
                    label = { Text("Nombres") })
                OutlinedTextField(value = apellidoPaterno,
                    onValueChange = { apellidoPaterno = it },
                    label = { Text("Apellido Paterno") })
                OutlinedTextField(value = apellidoMaterno,
                    onValueChange = { apellidoMaterno = it },
                    label = { Text("Apellido Materno") })
                OutlinedTextField(value = telefono,
                    onValueChange = {  input ->
                        // para permitir solo numeros
                        if (input.all { it.isDigit() }) {
                            telefono = input
                        } },
                    label = { Text("Teléfono") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") })
                OutlinedTextField(value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Direccion del contacto") })
            }
        },
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAgregarContactoDialog() {
    AgregarContactoDialog(
        onDismiss = {},
        onGuardar = {}
    )
}
