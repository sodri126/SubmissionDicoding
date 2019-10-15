package com.example.submission5.ui.favoritefilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission5.data.local.entity.MovieEntity
import com.example.submission5.data.repository.MovieRepository
import com.example.submission5.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val mutableListMovies = MutableLiveData<Result<List<MovieEntity>>>()

    init {
        getListMovies()
    }

    fun getLiveDataListMovie(): LiveData<Result<List<MovieEntity>>> = mutableListMovies

    private fun getListMovies() {
        viewModelScope.launch {
            mutableListMovies.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getLocalListMovies()
            }
            mutableListMovies.postValue(listMovie)
        }
    }
}