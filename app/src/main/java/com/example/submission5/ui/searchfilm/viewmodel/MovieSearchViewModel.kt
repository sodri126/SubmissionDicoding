package com.example.submission5.ui.searchfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission5.data.api.model.DiscoverMovie
import com.example.submission5.data.api.model.GeneralResponse
import com.example.submission5.data.repository.MovieRepository
import com.example.submission5.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieSearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val mutableListSearchMovies: MutableLiveData<Result<GeneralResponse<DiscoverMovie>>> = MutableLiveData()

    fun getLiveDataListMovie(): LiveData<Result<GeneralResponse<DiscoverMovie>>> = mutableListSearchMovies

    fun fetchSearchMovie(query: String) {
        viewModelScope.launch {
            mutableListSearchMovies.postValue(Result.Loading)
            val listMovie = withContext(Dispatchers.IO) {
                movieRepository.getListSearchMovies(query)
            }
            mutableListSearchMovies.postValue(listMovie)
        }
    }
}