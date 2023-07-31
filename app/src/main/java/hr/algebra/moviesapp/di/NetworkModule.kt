package hr.algebra.moviesapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.algebra.moviesapp.api.MoviesApi
import hr.algebra.moviesapp.api.PointsApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val POINTS_API_URL = "https://algebramovieapp-default-rtdb.firebaseio.com/"

private const val MOVIES_API_URL = "https://api.themoviedb.org/3/movie/"
const val MOVIE_IMAGES_API_URL = "https://image.tmdb.org/t/p/original"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton // created at - Application#onCreate, destroyed at - Application#onDestroy
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun providePointsApi(okHttpClient: OkHttpClient) : PointsApi {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(POINTS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PointsApi::class.java)
    }

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideMoviesApi(okHttpClient: OkHttpClient) : MoviesApi {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(MOVIES_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MoviesApi::class.java)
    }

}