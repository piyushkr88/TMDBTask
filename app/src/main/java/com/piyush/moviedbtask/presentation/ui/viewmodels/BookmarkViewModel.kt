package com.piyush.moviedbtask.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.moviedbtask.domain.model.Movie
import com.piyush.moviedbtask.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _bookmarkedMovies = MutableLiveData<List<Movie>>()
    val bookmarkedMovies: LiveData<List<Movie>> = _bookmarkedMovies

    init {
        loadBookmarkedMovies()
    }

    private fun loadBookmarkedMovies() {
        viewModelScope.launch {
            val result = repository.getBookmarkedMovies()
            _bookmarkedMovies.postValue(result)
        }
    }
}
