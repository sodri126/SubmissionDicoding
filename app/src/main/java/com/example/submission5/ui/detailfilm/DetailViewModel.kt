package com.example.submission5.ui.detailfilm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission5.data.local.entity.MovieEntity
import com.example.submission5.data.local.entity.TvShowEntity
import com.example.submission5.data.repository.MovieRepository
import com.example.submission5.ui.listfilm.activity.MainActivity
import com.example.submission5.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val movieRepository: MovieRepository,
    private val id: Int,
    private val tag: String
) : ViewModel() {
    private val liveDataDetailTvOrMovie = MutableLiveData<Any>()

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

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            movieRepository.deleteLocalMovie(movieId)
        }
    }

    fun insertMovie(movieEntity: MovieEntity) {
        viewModelScope.launch {
            movieRepository.insertLocalMovie(movieEntity)
        }
    }

    fun deleteTvShow(movieId: Int) {
        viewModelScope.launch {
            movieRepository.deleteLocalTvShow(movieId)
        }
    }

    fun insertTvShow(tvShow: TvShowEntity) {
        viewModelScope.launch {
            movieRepository.insertLocalTvShow(tvShow)
        }
    }

    fun fetchData(): LiveData<Any> = liveDataDetailTvOrMovie
}