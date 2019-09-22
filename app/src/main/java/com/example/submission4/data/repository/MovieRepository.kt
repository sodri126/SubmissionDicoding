package com.example.submission4.data.repository

import com.example.submission4.BuildConfig
import com.example.submission4.data.api.FilmService
import com.example.submission4.data.api.model.*
import com.example.submission4.data.local.FilmRoomDatabase
import com.example.submission4.data.local.entity.MovieEntity
import com.example.submission4.data.local.entity.TvShowEntity
import com.example.submission4.utils.Result
import com.example.submission4.utils.apiCall


class MovieRepository(private val service: FilmService, private val database: FilmRoomDatabase) {
    private val api: String = BuildConfig.API_KEY

    // web services
    suspend fun getListMovies() = apiCall {
        val response = service.getListMovies(api)
        return@apiCall if (response.isSuccessful) {
            val listDiscoverMovie = response.body() as DiscoverResponse<DiscoverMovie>
            for (discoverMovie in listDiscoverMovie.results) {
                discoverMovie.isFavorite = isFavoriteMovie(discoverMovie.id)
            }
            Result.Success(listDiscoverMovie)
        } else {
            Result.Error(response.message())
        }
    }

    suspend fun getListTvShows() = apiCall {
        val response = service.getListTvShow(api)
        return@apiCall if (response.isSuccessful) {
            val listDiscoverTv = response.body() as DiscoverResponse<DiscoverTv>
            for (discoverTv in listDiscoverTv.results) {
                discoverTv.isFavorite = isFavoriteTvShow(discoverTv.id)
            }
            Result.Success(listDiscoverTv)
        } else {
            Result.Error(response.message())
        }
    }

    suspend fun getDetailMovie(id: Int) = apiCall {
        val response = service.getDetailMovie(id, api)
        return@apiCall if (response.isSuccessful) {
            val detailMovie = response.body() as DetailMovie
            detailMovie.isFavorite = this.isFavoriteMovie(detailMovie.id)
            Result.Success(response.body() as DetailMovie)
        } else {
            Result.Error(response.message())
        }
    }

    suspend fun getDetailTv(id: Int) = apiCall {
        val response = service.getDetailTvShow(id, api)
        return@apiCall if (response.isSuccessful) {
            val detailTv = response.body() as DetailTv
            detailTv.isFavorite = this.isFavoriteTvShow(detailTv.id)
            Result.Success(response.body() as DetailTv)
        } else {
            Result.Error(response.message())
        }
    }

    // local api data movie
    suspend fun getLocalListMovies() = apiCall {
        val response = database.movieDao().getAllMovies()
        return@apiCall Result.Success(response)
    }

    private suspend fun isFavoriteMovie(movieId: Int): Boolean {
        val response = database.movieDao().getMovie(movieId)
        return response != null
    }

    suspend fun insertLocalMovie(movie: MovieEntity) {
        database.movieDao().insert(movie)
    }

    suspend fun deleteLocalMovie(movieId: Int) {
        database.movieDao().delete(movieId)
    }

    // tv shows
    suspend fun getLocalListTvShows() = apiCall {
        val response = database.tvShowDao().getAllTvs()
        return@apiCall Result.Success(response)
    }

    private suspend fun isFavoriteTvShow(tvShowId: Int): Boolean {
        val response = database.tvShowDao().getMovie(tvShowId)
        return response != null
    }

    suspend fun insertLocalTvShow(tvShow: TvShowEntity) {
        database.tvShowDao().insert(tvShow)
    }

    suspend fun deleteLocalTvShow(tvShowId: Int) {
        database.tvShowDao().delete(tvShowId)
    }
}