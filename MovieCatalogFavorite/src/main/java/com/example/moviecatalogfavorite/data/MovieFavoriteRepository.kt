package com.example.moviecatalogfavorite.data

import android.content.Context
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import com.example.moviecatalogfavorite.data.model.FavoriteTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieFavoriteRepository(private val context: Context?) {
    suspend fun getMovieFavoriteList(): List<FavoriteMovie> = withContext(Dispatchers.IO) {
        val listMovies = mutableListOf<FavoriteMovie>()
        context?.let {
            val movieFavoriteContentResolver = it.contentResolver.query(
                ContentProviderMovieCatalog.CONTENT_URI_FAVORITE_MOVIE,
                null,
                null,
                null,
                null,
                null
            )

            if (movieFavoriteContentResolver != null) {
                movieFavoriteContentResolver.moveToFirst()
                while (!movieFavoriteContentResolver.isAfterLast) {
                    listMovies.add(FavoriteMovie(movieFavoriteContentResolver))
                    movieFavoriteContentResolver.moveToNext()
                }
                movieFavoriteContentResolver.close()
            }
        }
        return@withContext listMovies
    }

    suspend fun getTvShowFavoriteList(): List<FavoriteTvShow> = withContext(Dispatchers.IO) {
        val listTvs = mutableListOf<FavoriteTvShow>()
        context?.let {
            val tvFavoriteContentResolver = it.contentResolver.query(
                ContentProviderMovieCatalog.CONTENT_URI_FAVORITE_TV,
                null,
                null,
                null,
                null,
                null
            )
            if (tvFavoriteContentResolver != null) {
                tvFavoriteContentResolver.moveToFirst()
                while (!tvFavoriteContentResolver.isAfterLast) {
                    listTvs.add(FavoriteTvShow(tvFavoriteContentResolver))
                    tvFavoriteContentResolver.moveToNext()

                }
                tvFavoriteContentResolver.close()
            }
        }
        return@withContext listTvs
    }
}