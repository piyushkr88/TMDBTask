package com.piyush.moviedbtask.data.remote.model

data class TrendingMoviesResponse(
    val page: Int,
    val results: List<TrendingMovies>
)
