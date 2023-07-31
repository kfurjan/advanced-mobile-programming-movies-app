package hr.algebra.moviesapp.view.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import hr.algebra.moviesapp.view.BottomNavScreen
import hr.algebra.moviesapp.view.main.AboutScreen
import hr.algebra.moviesapp.view.main.MapScreen
import hr.algebra.moviesapp.view.main.MoviesScreen
import hr.algebra.moviesapp.view.main.MoviesState
import hr.algebra.moviesapp.viewmodel.MapViewModel
import hr.algebra.moviesapp.viewmodel.MoviesViewModel

@ExperimentalPagingApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Movies.route
    ) {
        composable(route = BottomNavScreen.Movies.route) {
            val moviesViewModel = hiltViewModel<MoviesViewModel>()
            val moviesState = MoviesState(moviesViewModel)
            MoviesScreen(
                moviesState = moviesState,
                onUpdate = { moviesViewModel.update(it.copy(liked = true)) },
                onDelete = { moviesViewModel.delete(it) }
            )
        }
        composable(route = BottomNavScreen.Map.route) {
            val mapViewModel = hiltViewModel<MapViewModel>()
            MapScreen(mapState = mapViewModel.mapState.value)
        }
        composable(route = BottomNavScreen.About.route) {
            AboutScreen()
        }
    }
}