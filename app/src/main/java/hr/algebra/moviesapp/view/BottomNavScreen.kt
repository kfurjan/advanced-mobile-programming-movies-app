package hr.algebra.moviesapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import hr.algebra.moviesapp.R

sealed class BottomNavScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object Movies: BottomNavScreen(
        route = "movies",
        title = R.string.movies,
        icon = Icons.Default.Movie
    )
    object Map: BottomNavScreen(
        route = "map",
        title = R.string.map,
        icon = Icons.Default.Map
    )
    object About: BottomNavScreen(
        route = "about",
        title = R.string.about,
        icon = Icons.Default.Person
    )
}