package com.piyush.moviedbtask.di

import android.app.Application
import androidx.room.Room
import com.piyush.moviedbtask.data.local.dao.MovieDao
import com.piyush.moviedbtask.data.local.database.Converters
import com.piyush.moviedbtask.data.local.database.MovieDatabase
import org.koin.dsl.module

fun provideDataBase(application: Application): MovieDatabase =
     Room.databaseBuilder(
        application,
         MovieDatabase::class.java,
        "table_movie"
    ).
     fallbackToDestructiveMigration().build()

fun provideDao(postDataBase: MovieDatabase): MovieDao = postDataBase.getMovieDao()


val dataBaseModule= module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}