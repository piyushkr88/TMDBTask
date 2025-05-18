package com.piyush.moviedbtask.data.repository

import com.piyush.moviedbtask.data.local.dao.MovieDao
import com.piyush.moviedbtask.data.remote.api.ApiService
import com.piyush.moviedbtask.data.remote.mappers.toDomain
import com.piyush.moviedbtask.data.remote.mappers.toEntity
import com.piyush.moviedbtask.domain.model.Movie
import com.piyush.moviedbtask.domain.repository.MovieRepository
import com.piyush.moviedbtask.utils.Resource

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getNowPlayingMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = apiService.getNowPlayingMovies(page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val bookmarkedIds = movieDao.getBookmarkedMovieIds()

                    val entities = body.results.map {
                        val isBookmarked : Int = if(bookmarkedIds.contains(it.id)){
                            1
                        }else {
                            0
                        }
                        it.toEntity(category = "now_playing", isBookmarked = isBookmarked)
                    }

                    movieDao.insertAll(entities)
                    val domainMovies = entities.map { it.toDomain() }
                    Resource.Success(domainMovies)
                } else {
                    Resource.Error("Empty response body")
                }
            } else {
                val cached = movieDao.getMoviesByCategory("now_playing")
                if (cached.isNotEmpty()) {
                    Resource.Success(cached.map { it.toDomain() })
                } else {
                    Resource.Error("Error ${response.code()}: ${response.message()}")
                }
            }
        } catch (e: Exception) {
            val cached = movieDao.getMoviesByCategory("now_playing")
            if (cached.isNotEmpty()) {
                Resource.Success(cached.map { it.toDomain() })
            } else {
                Resource.Error("Network call failed: ${e.message ?: "Unknown error"}")
            }
        }
    }


    override suspend fun getTrendingMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = apiService.getTrendingMovies(page)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val bookmarkedIds = movieDao.getBookmarkedMovieIds()

                    val entities = body.results.map {
                        val isBookmarked : Int = if(bookmarkedIds.contains(it.id)){
                            1
                        }else {
                            0
                        }
                        it.toEntity(category = "trending", isBookmarked)
                    }

                    movieDao.insertAll(entities)

                    val domainMovies = entities.map { it.toDomain() }
                    Resource.Success(domainMovies)
                } else {
                    Resource.Error("Empty response body")
                }
            } else {
                val cached = movieDao.getMoviesByCategory("trending")
                if (cached.isNotEmpty()) {
                    Resource.Success(cached.map { it.toDomain() })
                } else {
                    Resource.Error("Error ${response.code()}: ${response.message()}")
                }
            }
        } catch (e: Exception) {
            val cached = movieDao.getMoviesByCategory("trending")
            if (cached.isNotEmpty()) {
                Resource.Success(cached.map { it.toDomain() })
            } else {
                Resource.Error("Network call failed: ${e.message ?: "Unknown error"}")
            }
        }
    }


    override suspend fun searchMovies(query: String): Resource<List<Movie>> {
        return try {
            val response = apiService.searchMovies(query)
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                Resource.Success(movies)
            } else {
                Resource.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    override suspend fun getBookmarkedMovies(): List<Movie> {
        return movieDao.getBookmarkedMovies().map { it.toDomain() }
    }

    override suspend fun updateBookmark(movie: Movie) {
        val favorites = movie.isBookmarked
        movieDao.updateBookmark(movie.id, favorites)
    }

    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toDomain()
    }
}
