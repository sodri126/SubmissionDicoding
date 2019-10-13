package com.example.submission4.ui.listfilm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.api.model.Response
import com.example.submission4.data.api.model.DiscoverTv
import com.example.submission4.data.repository.MovieRepository
import com.example.submission4.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val listTvLiveData: MutableLiveData<Result<Response<DiscoverTv>>> = MutableLiveData()

    init {
        fetchListTv()
    }

    private fun fetchListTv() {
        viewModelScope.launch {
            listTvLiveData.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getListTvShows()
            }
            listTvLiveData.postValue(listMovie)
        }
    }
}