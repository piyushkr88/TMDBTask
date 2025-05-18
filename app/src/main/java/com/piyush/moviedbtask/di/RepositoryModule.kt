package com.piyush.moviedbtask.di

import com.piyush.moviedbtask.data.repository.MovieRepositoryImpl
import com.piyush.moviedbtask.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(),get()) }
}