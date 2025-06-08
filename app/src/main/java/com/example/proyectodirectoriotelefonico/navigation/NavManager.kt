package com.example.proyectodirectoriotelefonico.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyectodirectoriotelefonico.dataStore.StoreBoarding
import com.example.proyectodirectoriotelefonico.onBoardViews.MainOnBoarding
import com.example.proyectodirectoriotelefonico.view.principalScreen
import com.example.proyectodirectoriotelefonico.view.SplashScreen
import com.example.proyectodirectoriotelefonico.viewModels.ContactoViewModel



@Composable
fun NavManager(){
    val context= LocalContext.current
    val dataStore= StoreBoarding(context)
    val store=dataStore.getStoreBoarding.collectAsState(initial = true)
    val contactoViewModel: ContactoViewModel = viewModel()
    val navController= rememberNavController()

    NavHost(navController=navController,
        startDestination= "Splash")
    {
        composable("onBoarding"){
            MainOnBoarding(navController,dataStore)
        }
        composable("home"){
            principalScreen(contactoViewModel)
        }
        composable("Splash"){
            SplashScreen(navController, store.value)
        }
    }
}