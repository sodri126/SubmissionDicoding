package com.example.submission5.ui.searchfilm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission5.data.local.entity.MovieEntity
import com.example.submission5.data.local.entity.TvShowEntity
import com.example.submission5.data.repository.MovieRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun addFavoriteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            movieRepository.insertLocalMovie(movie)
        }
    }

    fun addFavoriteTvShow(tvShow: TvShowEntity) {
        viewModelScope.launch {
            movieRepository.insertLocalTvShow(tvShow)
        }
    }

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            movieRepository.deleteLocalMovie(movieId)
        }
    }

    fun deleteTvShow(tvShowId: Int) {
        viewModelScope.launch {
            movieRepository.deleteLocalTvShow(tvShowId)
        }
    }
}