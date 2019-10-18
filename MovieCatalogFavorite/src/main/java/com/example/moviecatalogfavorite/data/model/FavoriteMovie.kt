package com.example.moviecatalogfavorite.data.model

import android.database.Cursor
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog.getColumnFloat
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog.getColumnInt
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog.getColumnString

data class FavoriteMovie(
    var movieId: Int = 0,
    var movieImagePath: String? = null,
    var movieTitle: String = "",
    var movieRate: Float = 0F,
    var movieOverview: String = "",
    var movieDate: String = ""
) {
    constructor(cursor: Cursor) : this() {
        this.movieId = getColumnInt(cursor, "movie_id")
        this.movieImagePath = getColumnString(cursor, "movie_image_path")
        this.movieTitle = getColumnString(cursor, "movie_title")
        this.movieRate = getColumnFloat(cursor, "movie_rate")
        this.movieOverview = getColumnString(cursor, "movie_overview")
        this.movieDate = getColumnString(cursor, "movie_date")
    }
}