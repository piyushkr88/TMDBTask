package com.piyush.moviedbtask.data.remote.mappers

import com.piyush.moviedbtask.data.local.entities.MovieEntity
import com.piyush.moviedbtask.data.remote.model.NowPlayingMovies
import com.piyush.moviedbtask.data.remote.model.TrendingMovies
import com.piyush.moviedbtask.domain.model.Movie

fun NowPlayingMovies.toDomain(): Movie = Movie(
    id = id,
    title = title,
    original_title = original_title,
    overview = overview,
    release_date = release_date,
    poster_path = poster_path,
    backdrop_path = backdrop_path,
    genre_ids = genre_ids,
    vote_average = vote_average,
    vote_count = vote_count,
    adult = adult,
    original_language = original_language,
    popularity = popularity,
    video = video
)

fun TrendingMovies.toDomain(): Movie = Movie(
    id = id,
    title = title,
    original_title = original_title,
    overview = overview,
    release_date = release_date,
    poster_path = poster_path,
    backdrop_path = backdrop_path,
    genre_ids = genre_ids,
    vote_average = vote_average,
    vote_count = vote_count,
    adult = adult,
    original_language = original_language,
    popularity = popularity,
    video = video
)

fun NowPlayingMovies.toEntity(category: String,isBookmarked: Int = 0): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        original_title = original_title,
        overview = overview,
        release_date = release_date,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids ?: emptyList(),
        vote_average = vote_average,
        vote_count = vote_count,
        adult = adult,
        original_language = original_language,
        popularity = popularity,
        video = video,
        category = category,
        isBookmarked = isBookmarked,
    )
}

fun TrendingMovies.toEntity(category: String,isBookmarked: Int = 0): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        original_title = original_title,
        overview = overview,
        release_date = release_date,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        vote_average = vote_average,
        vote_count = vote_count,
        adult = adult,
        original_language = original_language,
        popularity = popularity,
        video = video,
        category = category,
        isBookmarked = isBookmarked,
    )
}
fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    original_title = original_title,
    overview = overview,
    release_date = release_date,
    poster_path = poster_path,
    backdrop_path = backdrop_path,
    genre_ids = genre_ids,
    vote_average = vote_average,
    vote_count = vote_count,
    adult = adult,
    original_language = original_language,
    popularity = popularity,
    video = video
)

