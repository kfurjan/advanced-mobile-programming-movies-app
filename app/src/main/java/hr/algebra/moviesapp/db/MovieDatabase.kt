package hr.algebra.moviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.algebra.moviesapp.dao.MovieDao
import hr.algebra.moviesapp.dao.MovieRemoteKeysDao
import hr.algebra.moviesapp.model.Movie
import hr.algebra.moviesapp.model.MovieRemoteKeys

@Database(entities = [Movie::class, MovieRemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun movieRemoteKeysDao() : MovieRemoteKeysDao
}