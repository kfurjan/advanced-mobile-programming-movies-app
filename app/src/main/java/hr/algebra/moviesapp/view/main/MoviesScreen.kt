package hr.algebra.moviesapp.view.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hr.algebra.moviesapp.di.MOVIE_IMAGES_API_URL
import hr.algebra.moviesapp.model.Movie
import hr.algebra.moviesapp.R
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@ExperimentalPagingApi
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    moviesState: MoviesState,
    onUpdate: (Movie) -> Unit,
    onDelete: (Movie) -> Unit
) {
    val movies = moviesState.movies.collectAsLazyPagingItems()
    Scaffold {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 12.dp)
        ) {
            items(
                items = movies,
                key = { movie -> movie.id }
            ) { movie ->
                movie?.let {
                    MovieItem(
                        modifier,
                        movie = it,
                        onUpdate,
                        onDelete
                    )
                }
            }
        }
    }

}

@Composable
fun MovieItem(
    modifier: Modifier,
    movie: Movie,
    onUpdate: (Movie) -> Unit,
    onDelete: (Movie) -> Unit
) {
    val updateAction = SwipeAction(
        icon = {
            Icon(
                modifier = modifier.size(150.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.like),
                tint = Color.White
            )
        },
        background = MaterialTheme.colors.primaryVariant,
        onSwipe = { onUpdate(movie) }
    )
    val deleteAction = SwipeAction(
        icon = {
            Icon(
                modifier = modifier.size(150.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = Color.White
            )
        },
        background = MaterialTheme.colors.secondary,
        onSwipe = { onDelete(movie) }
    )

    SwipeableActionsBox(
        startActions = listOf(updateAction),
        endActions = listOf(deleteAction)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(modifier = modifier.padding(vertical = 5.dp)) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$MOVIE_IMAGES_API_URL${movie.poster}")
                            .crossfade(true)
                            .build(),
                        contentDescription = movie.title,
                        placeholder = painterResource(id = R.drawable.tmdb),
                        error = painterResource(id = R.drawable.tmdb),
                        contentScale = ContentScale.FillBounds,
                        modifier = modifier
                            .height(450.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Icon(
                        modifier = modifier
                            .size(100.dp)
                            .align(Alignment.BottomEnd),
                        imageVector = if (movie.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.like),
                        tint = MaterialTheme.colors.secondary
                    )
                }
                Text(
                    modifier = modifier.padding(top = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary,
                    text = "${movie.title} ${movie.date}"
                )
                Text(text = movie.overview)
            }
        }
    }
}
