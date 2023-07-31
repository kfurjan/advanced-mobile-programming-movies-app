package hr.algebra.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.algebra.moviesapp.model.Movie
import hr.algebra.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    val movies = repository.getMovies()

    fun update(movie: Movie) {
        viewModelScope.launch {
            repository.update(movie)
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }
}