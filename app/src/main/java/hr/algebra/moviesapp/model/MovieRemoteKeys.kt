package hr.algebra.moviesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys_table")
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
