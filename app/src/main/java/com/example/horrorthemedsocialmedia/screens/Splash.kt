package com.example.horrorthemedsocialmedia.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.horrorthemedsocialmedia.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController){

    ConstraintLayout(modifier = ) {

    }

    LaunchedEffect(key1 = true){
        delay(3000)
        navController.navigate(Routes.BottomNav.routes)
    }
}