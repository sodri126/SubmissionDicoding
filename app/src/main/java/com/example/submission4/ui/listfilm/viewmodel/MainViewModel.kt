package com.example.submission4.ui.listfilm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.local.entity.MovieEntity
import com.example.submission4.data.local.entity.TvShowEntity
import com.example.submission4.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
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