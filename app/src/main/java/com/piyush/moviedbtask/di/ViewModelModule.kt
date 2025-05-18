package com.piyush.moviedbtask.di

import com.piyush.moviedbtask.presentation.ui.viewmodels.BookmarkViewModel
import com.piyush.moviedbtask.presentation.ui.viewmodels.HomeViewModel
import com.piyush.moviedbtask.presentation.ui.viewmodels.MovieDetailViewModel
import com.piyush.moviedbtask.presentation.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel{ HomeViewModel(get())}
    viewModel{ SearchViewModel(get())}
    viewModel { BookmarkViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}