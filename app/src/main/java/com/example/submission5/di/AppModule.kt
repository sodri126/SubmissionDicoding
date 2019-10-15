package com.example.submission5.di

import com.example.submission5.ui.detailfilm.DetailActivity
import com.example.submission5.ui.detailfilm.DetailViewModel
import com.example.submission5.ui.favoritefilm.fragment.FavoriteFragment
import com.example.submission5.ui.favoritefilm.fragment.MovieFavoriteFragment
import com.example.submission5.ui.favoritefilm.fragment.TvShowFavoriteFragment
import com.example.submission5.ui.favoritefilm.viewmodel.MovieFavoriteViewModel
import com.example.submission5.ui.favoritefilm.viewmodel.TvShowFavoriteViewModel
import com.example.submission5.ui.listfilm.activity.MainActivity
import com.example.submission5.ui.listfilm.fragment.MovieFragment
import com.example.submission5.ui.listfilm.fragment.TvShowFragment
import com.example.submission5.ui.listfilm.viewmodel.MainViewModel
import com.example.submission5.ui.listfilm.viewmodel.MovieViewModel
import com.example.submission5.ui.listfilm.viewmodel.TvShowViewModel
import com.example.submission5.ui.searchfilm.SearchActivity
import com.example.submission5.ui.searchfilm.fragment.MovieSearchFragment
import com.example.submission5.ui.searchfilm.fragment.TvShowSearchFragment
import com.example.submission5.ui.searchfilm.viewmodel.MovieSearchViewModel
import com.example.submission5.ui.searchfilm.viewmodel.SearchViewModel
import com.example.submission5.ui.searchfilm.viewmodel.TvShowSearchViewModel
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

    scope(named<SearchActivity>()) {
        viewModel { SearchViewModel(get()) }
    }

    scope(named<MovieFavoriteFragment>()) {
        viewModel { MovieFavoriteViewModel(get()) }
    }

    scope(named<TvShowFavoriteFragment>()) {
        viewModel { TvShowFavoriteViewModel(get()) }
    }

    scope(named<MovieSearchFragment>()) {
        viewModel { MovieSearchViewModel(get()) }
    }

    scope(named<TvShowSearchFragment>()) {
        viewModel { TvShowSearchViewModel(get()) }
    }
}