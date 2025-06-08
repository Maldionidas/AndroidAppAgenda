package com.example.proyectodirectoriotelefonico.onBoardViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.cronoapps.R
import com.example.proyectodirectoriotelefonico.data.PageData
import com.example.proyectodirectoriotelefonico.dataStore.StoreBoarding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class)
@Composable
fun MainOnBoarding(navController: NavController,store: StoreBoarding){
    val items= ArrayList<PageData>()

    items.add(
        PageData(
            R.raw.robot,
            "BIENVENIDO",
            "A tu agenda"
        )
    )
    items.add(
        PageData(
            R.raw.libro,
            "Guarda",
            "A tus contactos"
        )
    )
    items.add(
        PageData(
            R.raw.personitas,
            "Comunicate",
            "con tus personas favoritas"
        )
    )

   val pagerState= rememberPagerState(
       pageCount = items.size,
       initialOffscreenLimit = 2,
       infiniteLoop = false,
       initialPage = 0
   )

    OnBoardingPager(
        item=items, pagerState=pagerState,modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White), navController,store
     )
}