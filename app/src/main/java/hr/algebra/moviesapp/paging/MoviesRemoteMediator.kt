package hr.algebra.moviesapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import hr.algebra.moviesapp.api.MoviesApi
import hr.algebra.moviesapp.db.MovieDatabase
import hr.algebra.moviesapp.model.Movie
import hr.algebra.moviesapp.model.MovieRemoteKeys

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, Movie>() {
    private val movieDao = movieDatabase.movieDao()
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        Log.d("MEDIATOR", loadType.toString())
        return try {

            val currentPage = when(loadType) {
                LoadType.REFRESH -> {
                    val movieRemoteKeys: MovieRemoteKeys? = getMovieRemoteKeysClosestToCurrentPosition(state)
                    movieRemoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val movieRemoteKeys: MovieRemoteKeys? = getMovieRemoteKeysForFirstItem(state)
                    val prevPage = movieRemoteKeys?.prevPage
                        ?: return MediatorResult.Success(movieRemoteKeys != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val movieRemoteKeys: MovieRemoteKeys? = getMovieRemoteKeysForLastItem(state)
                    val nextPage = movieRemoteKeys?.nextPage
                        ?: return MediatorResult.Success(movieRemoteKeys != null)
                    nextPage
                }
            }

            val response = moviesApi.getMovies(page = currentPage)
            val endOfPaginationReached = response.movies.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteMovies()
                    movieRemoteKeysDao.deleteMovieRemoteKeys()
                }
                val movieRemoteKeys = response.movies.map { movie ->
                    MovieRemoteKeys(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                movieRemoteKeysDao.addMovieRemoteKeys(movieRemoteKeys = movieRemoteKeys)
                movieDao.addMovies(movies = response.movies)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getMovieRemoteKeysForFirstItem(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            movieRemoteKeysDao.getMovieRemoteKeys(id = movie.id)
        }
    }

    private suspend fun getMovieRemoteKeysForLastItem(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { movie ->
            movieRemoteKeysDao.getMovieRemoteKeys(id = movie.id)
        }
    }

    private suspend fun getMovieRemoteKeysClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getMovieRemoteKeys(id = id)
            }
        }
    }
}