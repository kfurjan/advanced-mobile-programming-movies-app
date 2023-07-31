package hr.algebra.moviesapp.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PageResult(
    @SerialName("results")
    val movies: List<Movie>
)
