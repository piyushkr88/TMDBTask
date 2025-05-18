package com.piyush.moviedbtask.data.remote.model

import com.piyush.moviedbtask.domain.model.Movie

data class SearchMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
