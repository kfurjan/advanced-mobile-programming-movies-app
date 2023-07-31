package hr.algebra.moviesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "movies_table")
@kotlinx.serialization.Serializable
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @kotlinx.serialization.Transient
    val movieId: Int = 0,
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("release_date")
    val date: String,
    @kotlinx.serialization.Transient
    var liked: Boolean = false
)
