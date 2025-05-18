package com.piyush.moviedbtask.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.moviedbtask.domain.model.Movie
import com.piyush.moviedbtask.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            val movieFromDb = repository.getMovieById(movieId)
            movieFromDb?.let {
                _movie.value = it
            }
        }
    }

    fun toggleBookmark() {
        _movie.value?.let { current ->
            val updatedMovie = current.copy(isBookmarked = current.isBookmarked)
            viewModelScope.launch {
                repository.updateBookmark(updatedMovie)
                _movie.value = updatedMovie
            }
        }
    }
}
