package com.dfeverx.dcert.ui.layout

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dfeverx.dcert.ui.component.MyBottomNavigation
import com.dfeverx.dcert.ui.component.Screen

@Composable
fun Main(navController: NavController, dismissAction:()->(Unit)) {
    Surface(color = MaterialTheme.colors.background) {
        BottomNavContent(dismissAction)
    }
}

@Composable
fun BottomNavContent(dismissAction:()->(Unit)) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    val items = listOf(
        Screen.Home,
        Screen.Profile,
    )
    Scaffold(
        bottomBar = { MyBottomNavigation(items, navController, currentRoute,dismissAction) },
        content = {
            MainContent(navController)
        }
    )
}

@Composable
fun MainContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home(navController = navController)
        }
      composable("pro") {
               Profile(navController = navController)
           }

    }
}



