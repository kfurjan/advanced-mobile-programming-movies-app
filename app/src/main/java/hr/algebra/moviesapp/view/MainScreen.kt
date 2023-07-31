package hr.algebra.moviesapp.view

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import hr.algebra.moviesapp.view.nav.BottomNavGraph

@ExperimentalPagingApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        BottomNavScreen::class.sealedSubclasses.forEach {
            AddItem(
                screen = it.objectInstance!!,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }


}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = { Icon(imageVector = screen.icon, contentDescription = stringResource(id = screen.title))},
        label = { Text(text = stringResource(id = screen.title))},
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = { navController.navigate(screen.route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        } })
}
