package hr.algebra.moviesapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hr.algebra.moviesapp.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_table")
    fun getMovies() : PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteMovies()

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}