package com.piyush.moviedbtask.data.remote.model

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlayingMovies>,
)