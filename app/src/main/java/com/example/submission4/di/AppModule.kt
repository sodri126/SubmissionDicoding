package com.example.submission4.di

import com.example.submission4.ui.detailfilm.DetailActivity
import com.example.submission4.ui.detailfilm.DetailViewModel
import com.example.submission4.ui.favoritefilm.fragment.FavoriteFragment
import com.example.submission4.ui.favoritefilm.fragment.MovieFavoriteFragment
import com.example.submission4.ui.favoritefilm.fragment.TvShowFavoriteFragment
import com.example.submission4.ui.favoritefilm.viewmodel.MovieFavoriteViewModel
import com.example.submission4.ui.favoritefilm.viewmodel.TvShowFavoriteViewModel
import com.example.submission4.ui.listfilm.activity.MainActivity
import com.example.submission4.ui.listfilm.fragment.MovieFragment
import com.example.submission4.ui.listfilm.fragment.TvShowFragment
import com.example.submission4.ui.listfilm.viewmodel.MainViewModel
import com.example.submission4.ui.listfilm.viewmodel.MovieViewModel
import com.example.submission4.ui.listfilm.viewmodel.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
    }
    single { FavoriteFragment() }
    scope(named<MovieFragment>()) {
        viewModel { MovieViewModel(get()) }
    }

    scope(named<TvShowFragment>()) {
        viewModel { TvShowViewModel(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int, tag: String) -> DetailViewModel(get(), id, tag) }
    }

    scope(named<MovieFavoriteFragment>()) {
        viewModel { MovieFavoriteViewModel(get()) }
    }

    scope(named<TvShowFavoriteFragment>()) {
        viewModel { TvShowFavoriteViewModel(get()) }
    }
}