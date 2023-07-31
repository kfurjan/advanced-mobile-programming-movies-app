package hr.algebra.moviesapp.api

import hr.algebra.moviesapp.BuildConfig
import hr.algebra.moviesapp.model.PageResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("popular")
    suspend fun getMovies(
        @Query("api_key") api_key: String = BuildConfig.TMDB_KEY,
        @Query("page") page: Int = 1
    ) : PageResult
}