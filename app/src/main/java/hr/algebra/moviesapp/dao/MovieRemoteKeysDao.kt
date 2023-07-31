package hr.algebra.moviesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hr.algebra.moviesapp.model.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {
    @Query("SELECT * FROM movie_remote_keys_table WHERE id=:id")
    suspend fun getMovieRemoteKeys(id: Int) : MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieRemoteKeys(movieRemoteKeys: List<MovieRemoteKeys>)

    @Query("DELETE FROM movie_remote_keys_table")
    suspend fun deleteMovieRemoteKeys()
}