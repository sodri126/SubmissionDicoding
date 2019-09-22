package com.example.submission4.di

import com.example.submission4.data.local.FilmRoomDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        FilmRoomDatabase.getDatabase(get())
    }
}