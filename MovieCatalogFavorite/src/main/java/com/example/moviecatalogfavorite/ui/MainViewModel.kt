package com.example.moviecatalogfavorite.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogfavorite.data.MovieFavoriteRepository
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val favoriteRepository: MovieFavoriteRepository by lazy { MovieFavoriteRepository(application) }

    private val favoriteMutableLiveData: MutableLiveData<List<FavoriteMovie>> = MutableLiveData()
    fun favoriteLiveData(): LiveData<List<FavoriteMovie>> = favoriteMutableLiveData

    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    fun isLoading(): LiveData<Boolean> = loading

    fun getListMovieFavorite() {
        viewModelScope.launch {
            loading.value = true
            favoriteMutableLiveData.value = favoriteRepository.getMovieFavoriteList()
            loading.value = false
        }
    }
}