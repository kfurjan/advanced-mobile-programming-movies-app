package hr.algebra.moviesapp.view.main

import hr.algebra.moviesapp.model.Point

data class MapState(
    val points: List<Point> = emptyList(),
    val loading: Boolean = true
)