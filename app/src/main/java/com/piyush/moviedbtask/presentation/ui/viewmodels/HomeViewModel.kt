package com.piyush.moviedbtask.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.moviedbtask.domain.model.Movie
import com.piyush.moviedbtask.domain.repository.MovieRepository
import com.piyush.moviedbtask.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _trendingMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val trendingMovies: StateFlow<Resource<List<Movie>>> get() = _trendingMovies

    private val _nowPlayingMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val nowPlayingMovies: StateFlow<Resource<List<Movie>>> get() = _nowPlayingMovies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _trendingMovies.value = Resource.Loading
            _nowPlayingMovies.value = Resource.Loading

            val trendingDeferred = async { repository.getTrendingMovies(1) }
            val nowPlayingDeferred = async { repository.getNowPlayingMovies(1) }

            when (val trendingResult = trendingDeferred.await()) {
                is Resource.Success -> _trendingMovies.value = Resource.Success(trendingResult.data)
                is Resource.Error -> _trendingMovies.value = Resource.Error(trendingResult.message)
                else -> {}
            }

            when (val nowPlayingResult = nowPlayingDeferred.await()) {
                is Resource.Success -> _nowPlayingMovies.value = Resource.Success(nowPlayingResult.data)
                is Resource.Error -> _nowPlayingMovies.value = Resource.Error(nowPlayingResult.message)
                else -> {}
            }
        }
    }
}
