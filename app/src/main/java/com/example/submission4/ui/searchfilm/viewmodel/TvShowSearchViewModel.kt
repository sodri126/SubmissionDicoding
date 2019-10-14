package com.example.submission4.ui.searchfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.api.model.GeneralResponse
import com.example.submission4.data.api.model.DiscoverTv
import com.example.submission4.data.repository.MovieRepository
import com.example.submission4.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowSearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val mutableListTvShowMovies: MutableLiveData<Result<GeneralResponse<DiscoverTv>>> = MutableLiveData()

    fun getLiveDataListTvShow(): LiveData<Result<GeneralResponse<DiscoverTv>>> = mutableListTvShowMovies

    fun fetchSearchTvShow(query: String) {
        viewModelScope.launch {
            mutableListTvShowMovies.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getListSearchTvShows(query)
            }
            mutableListTvShowMovies.postValue(listMovie)
        }
    }
}