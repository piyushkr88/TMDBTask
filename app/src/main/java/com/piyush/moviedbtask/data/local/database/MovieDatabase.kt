package com.piyush.moviedbtask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piyush.moviedbtask.data.local.dao.MovieDao
import com.piyush.moviedbtask.data.local.entities.MovieEntity

@Database(entities = [(MovieEntity::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
}