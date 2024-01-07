package com.example.horrorthemedsocialmedia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.horrorthemedsocialmedia.screens.AddThreads
import com.example.horrorthemedsocialmedia.screens.BottomNav
import com.example.horrorthemedsocialmedia.screens.Home
import com.example.horrorthemedsocialmedia.screens.Notification
import com.example.horrorthemedsocialmedia.screens.Profile
import com.example.horrorthemedsocialmedia.screens.Search
import com.example.horrorthemedsocialmedia.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.routes)
    {
        composable(Routes.Splash.routes){
            Splash(navController = navController)
        }
        composable(Routes.Home.routes){
            Home()
        }
        composable(Routes.Notification.routes){
            Notification()
        }
        composable(Routes.Search.routes){
            Search()
        }
        composable(Routes.AddThread.routes){
            AddThreads()
        }
        composable(Routes.Profile.routes){
            Profile()
        }
        composable(Routes.BottomNav.routes){
            BottomNav(navController = navController)
        }

    }
}