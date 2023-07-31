package hr.algebra.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import hr.algebra.moviesapp.ui.theme.MoviesAppTheme
import hr.algebra.moviesapp.view.nav.RootNavGraph

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                Surface {
                    RootNavGraph(navController = rememberNavController())
                }
            }
        }

    }
}

