package com.example.moviecatalogfavorite.data

import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieFavoriteRepository(private val context: Context) {
    suspend fun getMovieFavoriteList(): List<FavoriteMovie> = withContext(Dispatchers.IO) {
        val listMovies = mutableListOf<FavoriteMovie>()
        val movieFavoriteContentResolver = context.contentResolver.query(ContentProviderMovieCatalog.CONTENT_URI, null, null, null, null, null)

        if (movieFavoriteContentResolver != null) {
            movieFavoriteContentResolver.moveToFirst()
            while(!movieFavoriteContentResolver.isAfterLast) {
                listMovies.add(FavoriteMovie(movieFavoriteContentResolver))
                movieFavoriteContentResolver.moveToNext()
            }
            movieFavoriteContentResolver.close()
        }
        return@withContext listMovies
    }
}