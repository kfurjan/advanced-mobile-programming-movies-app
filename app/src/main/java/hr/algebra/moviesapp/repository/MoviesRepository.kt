package hr.algebra.moviesapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Delete
import androidx.room.Update
import hr.algebra.moviesapp.api.MoviesApi
import hr.algebra.moviesapp.db.MovieDatabase
import hr.algebra.moviesapp.model.Movie
import hr.algebra.moviesapp.paging.MoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase
) {
    fun getMovies(): Flow<PagingData<Movie>> {
        val pagingSource = { movieDatabase.movieDao().getMovies() }

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MoviesRemoteMediator(
                moviesApi,
                movieDatabase
            ),
            pagingSourceFactory = pagingSource
        ).flow
    }

    suspend fun update(movie: Movie) = movieDatabase.movieDao().update(movie)

    suspend fun delete(movie: Movie) = movieDatabase.movieDao().delete(movie)

}