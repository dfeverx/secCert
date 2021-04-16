package com.dfeverx.dcert.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.dfeverx.dcert.R


@Composable
fun MyBottomNavigation(
    items: List<Screen>,
    navController: NavHostController,
    currentRoute: String?,
    dismissAction: () -> (Unit)
) {

    BottomNavigation(elevation = 16.dp, backgroundColor = MaterialTheme.colors.primary) {
        items.forEach { screen ->
            BottomNavigationItem(

                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.label)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo = navController.graph.startDestination
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true

                    }
                }
            )
        }
    }
}

sealed class Screen(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Filled.Home)
    object Profile : Screen("pro", R.string.home, Icons.Filled.Favorite)
//    object Favorites : Screen("favorites_item", R.string.favorites, Icons.Outlined.FavoriteBorder)
//    object Profile : Screen("profile_item", R.string.profile, Icons.Filled.AccountCircle)
//    object Cart : Screen("cart_item", R.string.cart, Icons.Filled.ShoppingCart)
}