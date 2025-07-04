package com.example.proyectodirectoriotelefonico.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cronoapps.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, store: Boolean?) {
    var screen by remember { mutableStateOf("") }
    screen = if (store == true) "home" else "onBoarding"

    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.navigate(screen) {
            popUpTo(0)
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.profiling2),
            contentDescription = "Logo",
            Modifier.size(150.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    // Simula el NavController y el valor de store
    val navController = rememberNavController()
    SplashScreen(navController = navController, store = true)
}
