package com.piyush.moviedbtask.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.moviedbtask.data.repository.MovieRepositoryImpl
import com.piyush.moviedbtask.domain.repository.MovieRepository
import com.piyush.moviedbtask.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun search(query: String) {
        _query.value = query
    }

    val searchResults = _query
        .debounce(500)
        .filter { it.length > 2 }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                emit(Resource.Loading)
                emit(repository.searchMovies(query))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Resource.Success(emptyList()))
}
