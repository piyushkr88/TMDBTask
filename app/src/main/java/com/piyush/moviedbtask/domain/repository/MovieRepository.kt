package com.piyush.moviedbtask.domain.repository

import com.piyush.moviedbtask.domain.model.Movie
import com.piyush.moviedbtask.utils.Resource

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Resource<List<Movie>>
    suspend fun getTrendingMovies(page: Int): Resource<List<Movie>>
    suspend fun searchMovies(query: String): Resource<List<Movie>>
    suspend fun getBookmarkedMovies(): List<Movie>
    suspend fun updateBookmark(movie: Movie)
    suspend fun getMovieById(id: Int): Movie?
}
