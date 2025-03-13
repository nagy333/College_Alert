package com.example.collegealert

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.collegealert.screens.AddEventScreen
import com.example.collegealert.screens.EventsScreen
@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =Screens.EventsScreen.route ) {
        composable(route = Screens.EventsScreen.route){ EventsScreen(navController = navController) }
        composable(route = Screens.AddEventScreen.route,
            arguments = listOf(navArgument(name = "data"){
                type = NavType.IntType
            })
        ){navBackStackEntry->
            val data = navBackStackEntry.arguments?.getInt("data")?:-1
            AddEventScreen(navController = navController, data = data) }
    }
}
sealed class Screens(val route : String){
    object EventsScreen: Screens(route = "EventsScreen")
    object AddEventScreen: Screens(route = "AddEventScreen/{data}")
    fun createRoute(data: Int) :String = "AddEventScreen/$data"
}