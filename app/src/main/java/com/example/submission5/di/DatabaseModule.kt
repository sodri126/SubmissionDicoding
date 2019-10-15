package com.example.submission5.di

import com.example.submission5.data.local.FilmRoomDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        FilmRoomDatabase.getDatabase(get())
    }
}