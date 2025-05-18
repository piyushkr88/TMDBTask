package com.piyush.moviedbtask.data.remote.api
import com.piyush.moviedbtask.data.remote.model.NowPlayingResponse
import com.piyush.moviedbtask.data.remote.model.SearchMovieResponse
import com.piyush.moviedbtask.data.remote.model.TrendingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "hn-IN"
    ): Response<NowPlayingResponse>

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TrendingMoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<SearchMovieResponse>
}