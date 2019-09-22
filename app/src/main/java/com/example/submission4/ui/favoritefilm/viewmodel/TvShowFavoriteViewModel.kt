package com.example.submission4.ui.favoritefilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.local.entity.TvShowEntity
import com.example.submission4.data.repository.MovieRepository
import com.example.submission4.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val mutableListTvShows = MutableLiveData<Result<List<TvShowEntity>>>()

    init {
        getListMovies()
    }

    fun getLiveDataListTv(): LiveData<Result<List<TvShowEntity>>> = mutableListTvShows

    private fun getListMovies() {
        viewModelScope.launch {
            mutableListTvShows.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getLocalListTvShows()
            }
            mutableListTvShows.postValue(listMovie)
        }
    }
}