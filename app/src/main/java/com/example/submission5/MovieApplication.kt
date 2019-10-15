package com.example.submission5

import android.app.Application
import com.example.submission5.di.appModule
import com.example.submission5.di.databaseModule
import com.example.submission5.di.networkModule
import com.example.submission5.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@MovieApplication)
            modules(listOf(appModule, networkModule, repositoryModule, databaseModule))
        }
    }
}