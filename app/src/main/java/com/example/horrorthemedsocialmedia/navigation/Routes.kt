package com.example.horrorthemedsocialmedia.navigation

sealed class Routes(val routes: String) {

    object Home : Routes("home")
    object Notification : Routes("notification")
    object Profile : Routes("profile")
    object Search : Routes("search")
    object Splash : Routes("splash")
    object AddThread : Routes("add_threads")
    object BottomNav : Routes("bottom_nav")


}
