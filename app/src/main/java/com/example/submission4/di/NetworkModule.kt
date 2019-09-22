package com.example.submission4.di

import com.example.submission4.data.api.FilmServiceFactory
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val networkModule = module {
    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create()
    }
    single { FilmServiceFactory.settingOkHttpClient() }
    single { FilmServiceFactory.getService(get(), get()) }
}
