package com.piyush.moviedbtask

import android.app.Application
import com.piyush.moviedbtask.di.dataBaseModule
import com.piyush.moviedbtask.di.networkModule
import com.piyush.moviedbtask.di.repositoryModule
import com.piyush.moviedbtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieDBApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieDBApp)
            androidLogger()
            modules(networkModule, repositoryModule, viewModelModule,
                dataBaseModule)
        }
    }
}