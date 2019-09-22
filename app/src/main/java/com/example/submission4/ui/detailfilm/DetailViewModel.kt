package com.example.submission4.ui.detailfilm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission4.data.repository.MovieRepository
import com.example.submission4.ui.listfilm.activity.MainActivity
import com.example.submission4.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val movieRepository: MovieRepository, private val id: Int, private val tag: String): ViewModel() {
    private val liveDataDetailTvOrMovie =  MutableLiveData<Any>()

    init {
        fetchDetailMovieOrTv(this.id, this.tag)
    }

    private fun fetchDetailMovieOrTv(id: Int, tag: String) {
        viewModelScope.launch {
            liveDataDetailTvOrMovie.postValue(Result.Loading)
            val data = withContext(Dispatchers.IO) {
                if (tag == MainActivity.TAG_TV) {
                    return@withContext movieRepository.getDetailTv(id)
                } else {
                    return@withContext movieRepository.getDetailMovie(id)
                }
            }
            liveDataDetailTvOrMovie.postValue(data)
        }
    }

    fun fetchData(): LiveData<Any> = liveDataDetailTvOrMovie
}