package hr.algebra.moviesapp.view.main

import androidx.paging.ExperimentalPagingApi
import hr.algebra.moviesapp.viewmodel.MoviesViewModel

@ExperimentalPagingApi
class MoviesState(moviesViewModel: MoviesViewModel) {
    val movies = moviesViewModel.movies
}