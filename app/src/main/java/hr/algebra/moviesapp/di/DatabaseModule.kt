package hr.algebra.moviesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hr.algebra.moviesapp.db.MovieDatabase
import javax.inject.Singleton

private const val MOVIE_DATABASE = "movie_database"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton // created at - Application#onCreate, destroyed at - Application#onDestroy
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MOVIE_DATABASE
        ).build()
    }
}