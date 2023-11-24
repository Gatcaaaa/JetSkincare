package com.latihan.jetsubmission.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Keranjang: Screen("Keranjang")
    object Profile: Screen("profile")
    object DetailSkincare: Screen("home/{skincareId}"){
        fun createRoute(skincareId: Long) = "home/$skincareId"
    }
}
