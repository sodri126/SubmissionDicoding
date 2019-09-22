package com.example.submission4.ui.listfilm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.api.model.DiscoverMovie
import com.example.submission4.data.api.model.DiscoverResponse
import com.example.submission4.data.repository.MovieRepository
import com.example.submission4.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val listMovieLiveData: MutableLiveData<Result<DiscoverResponse<DiscoverMovie>>> =
        MutableLiveData()

    init {
        fetchListMovie()
    }

    private fun fetchListMovie() {
        viewModelScope.launch {
            listMovieLiveData.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getListMovies()
            }
            listMovieLiveData.postValue(listMovie)
        }
    }
}