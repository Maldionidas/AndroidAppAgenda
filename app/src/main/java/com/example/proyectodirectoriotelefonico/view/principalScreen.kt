@file:Suppress("DEPRECATION")

package com.example.proyectodirectoriotelefonico.view

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.cronoapps.R
import com.example.proyectodirectoriotelefonico.model.Contacto
import com.example.proyectodirectoriotelefonico.viewModels.ContactoViewModel


@SuppressLint("ComposableNaming")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun principalScreen(viewModel: ContactoViewModel) {
    //recolecta los contactos que se encuentran en la bd
    val contactos by viewModel.contactos.collectAsState()
    //estas son variables para recordar los estados de los botones
    var mostrarDialogo by remember { mutableStateOf(false) }
    var contactoSeleccionado by remember { mutableStateOf<Contacto?>(null) }
    var alarmaActiva by remember { mutableStateOf(false) }
    var mostrarEmergencias by remember { mutableStateOf(false) }
//la fuente
    val poppins = FontFamily(Font(R.font.poppins))
    //contexto en local
    val context = LocalContext.current
    //alarma de el boton de emergencia
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.alarm) }
    //para las imagenes
    var mostrarImagenCompleta by remember { mutableStateOf(false) }
    var imagenSeleccionadaUri by remember { mutableStateOf<String?>(null) }



    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            //boton agregar contacto
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    contactoSeleccionado = null
                    mostrarDialogo = true
                }) {
                    Text(
                        "AGREGAR CONTACTO",
                        modifier = Modifier.padding(15.dp),
                        //fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = poppins
                    )
                }

            },
            //es la barra de hasta arriba, contiene el nombre de la app y el boton de emergencia
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Directorio Telefónico",
                                fontFamily = poppins,
                                fontSize = 25.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "EMERGENCIAS",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clickable { mostrarEmergencias = true }

                            )

                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF1E3259)
                    )
                )
            }
        ) { padding ->
            //si aun no existen contactos, despliega un mensaje que dice que no tienes contactos
            if (contactos.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Text("Sin contactos aún", fontSize = 20.sp)
                }
            } else {
                //si existen contactos, los acomoda en un lazycolumns
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    //agrega item por item segun el id
                    items(contactos, key = { it.id }) { contacto ->
                        //esta variable es para el swipe a la hora de querer eliminar un contacto
                        val dismissState = rememberDismissState()
                        //si haces completamente el swipe, elimina el contacto
                        if (dismissState.isDismissed(DismissDirection.StartToEnd) ||
                            dismissState.isDismissed(DismissDirection.EndToStart)
                        ) {
                            LaunchedEffect(contacto) {
                                viewModel.eliminarContacto(contacto)
                            }
                        }

                        //es el background que tiene el item para darle esa animacion
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(
                                DismissDirection.StartToEnd,
                                DismissDirection.EndToStart
                            ),
                            background = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.StartToEnd, DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
                                    null -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Eliminar", color = Color.White)
                                }
                            },
                            dismissContent = {
                                //cuando clickeas un contacto, abre el agregar contacto,
                                // pero con la informacion ya precargada de el contacto
                                ContactoItem(
                                    contacto = contacto,
                                    onClick = {
                                        contactoSeleccionado = contacto
                                        mostrarDialogo = true
                                    },
                                    onFotoClick = {
                                        imagenSeleccionadaUri = contacto.fotoUri
                                        mostrarImagenCompleta = true
                                    },
                                    context = context
                                )

                            }
                        )
                    }
                }

            }
            //si el boton agregar contacto es seleccionado,
            //abre view de agregar contacto
            if (mostrarDialogo) {
                AgregarContactoDialog(
                    onDismiss = { mostrarDialogo = false },
                    contactoExistente = contactoSeleccionado,
                    onGuardar = {
                        if (it.id != 0L) viewModel.actualizarContacto(it)
                        else viewModel.agregarContacto(it)
                    }
                )
            }
            //para mostrar el view de emergencias al clicar el boton
            if (mostrarEmergencias) {
                ModalBottomSheet(
                    onDismissRequest = { mostrarEmergencias = false }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Números de Emergencia",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        EmergencyNumberButton("911 - Emergencias generales", "911", context)
                        EmergencyNumberButton("089 - Denuncia anónima", "089", context)
                        EmergencyNumberButton("Cruz Roja", "5553951111", context)
                        EmergencyNumberButton("Bomberos", "57682222", context)
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón de pánico grande y llamativo
                    androidx.compose.material3.Button(
                        onClick = @androidx.annotation.RequiresPermission(android.Manifest.permission.VIBRATE) {
                            // Vibrar fuerte
                            val vibrator =
                                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                            if (!alarmaActiva) {
                                // Vibrar
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(
                                        VibrationEffect.createOneShot(
                                            5000,
                                            VibrationEffect.DEFAULT_AMPLITUDE
                                        )
                                    )
                                } else {
                                    vibrator.vibrate(5000)
                                }

                                // Activar sonido en bucle
                                mediaPlayer.isLooping = true
                                mediaPlayer.start()
                                alarmaActiva = true
                            } else {
                                // Detener sonido
                                mediaPlayer.stop()
                                mediaPlayer.prepare() // necesario para reiniciar después
                                alarmaActiva = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Botón de pánico",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (alarmaActiva) "DETENER ALARMA" else "BOTÓN DE PÁNICO",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }
        //para mostrar la imagen en grande
        if (mostrarImagenCompleta) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .clickable { mostrarImagenCompleta = false },
                contentAlignment = Alignment.Center
            ) {
                val scale by animateFloatAsState(targetValue = 1f)

                if (imagenSeleccionadaUri != null) {
                    AsyncImage(
                        model = imagenSeleccionadaUri,
                        contentDescription = "Imagen expandida",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .scale(scale)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Imagen predeterminada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .scale(scale)
                    )
                }

                IconButton(
                    onClick = { mostrarImagenCompleta = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar imagen",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

//aqui esta la config de como se muestra cada contacto
@Composable
fun ContactoItem(
    contacto: Contacto,
    onClick: () -> Unit,
    onFotoClick: () -> Unit,
    context: Context
) {
    //para la imagen
    val primeraPalabra = contacto.direccion.take(13)
    val poppins = FontFamily(Font(R.font.poppins))
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp)
            .then(
                Modifier
                    .drawBehind {
                        // Dibuja el borde superior
                        drawLine(
                            color = Color(0xFF1E3259),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2f
                        )
                        // Dibuja el borde inferior
                        drawLine(
                            color = Color(0xFF1E3259),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2f
                        )
                    }
            ),

        horizontalArrangement = Arrangement.Start, // Alinear a la izquierda
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // foto de usuario
        if (contacto.fotoUri != null) {
            AsyncImage(
                model = contacto.fotoUri,
                contentDescription = "Foto del contacto",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .clickable { onFotoClick() } // nueva acción
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Imagen por defecto",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .clickable { onFotoClick() }
            )
        }


        // Columna para los datos del contacto (a la derecha del ícono)
        Column(modifier = Modifier.padding(start = 10.dp)) { // Alineación a la derecha del ícono
            Text(
                "${contacto.nombres} ${contacto.apellidoPaterno} ${contacto.apellidoMaterno}",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(6.dp),
                fontSize = 26.sp,
                fontFamily = poppins
            )

            // Row para alinear el ícono con el texto del teléfono
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    contacto.telefono,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(6.dp),
                    fontSize = 25.sp,
                    fontFamily = poppins
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre el ícono y el texto
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Teléfono",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            // Crear el Intent para abrir la aplicación de llamadas
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel:${contacto.telefono}".toUri() // Número del contacto
                            }
                            context.startActivity(intent) // Iniciar la actividad de llamadas
                        },
                    Color(0xFF1E3259),

                    )
            }

            // Row para el correo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    contacto.correo.take(13),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(6.dp),
                    fontSize = 25.sp,
                    fontFamily = poppins
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre el ícono y el texto
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = "mailto:${contacto.correo}".toUri() // Aquí se usa mailto
                            }
                            context.startActivity(intent) // Inicia la actividad de correo
                        },
                    Color(0xFF1E3259)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    primeraPalabra,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(6.dp),
                    fontSize = 25.sp,
                    fontFamily = poppins
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Dirección",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            // Crear el intent para abrir la aplicación de mapas con la dirección
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data =
                                    "geo:0,0?q=${Uri.encode(contacto.direccion)}".toUri() // Utiliza "geo:" para Google Maps
                            }
                            context.startActivity(intent) // Inicia la actividad con el Intent
                        },
                    tint = Color(0xFF1E3259) // Establece el color del ícono
                )

            }
        }
    }


}

@Composable
fun EmergencyNumberButton(label: String, number: String, context: Context) {
    androidx.compose.material3.Button(
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$number".toUri()
            }
            context.startActivity(intent)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3259))
    ) {
        Icon(Icons.Default.Phone, contentDescription = "Llamar", tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label, color = Color.White,
            fontSize = 23.sp
        )
    }
}
