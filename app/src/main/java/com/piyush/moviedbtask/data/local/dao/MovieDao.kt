package com.piyush.moviedbtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piyush.moviedbtask.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    suspend fun getBookmarkedMovies(): List<MovieEntity>


    @Query("UPDATE movies SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Int, isBookmarked: Int)

    @Query("SELECT id FROM movies WHERE isBookmarked = 1")
    suspend fun getBookmarkedMovieIds(): List<Int>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}