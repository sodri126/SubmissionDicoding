package com.example.submission5.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submission5.data.local.FilmRoomDatabase

class FavoriteMovieProvider : ContentProvider() {
    companion object {
        private const val TABLE_FAVORITE_MOVIE = "FAVORITE_MOVIE"
        private const val TABLE_FAVORITE_TV = "FAVORITE_TV"
        private const val AUTHORITY = "com.example.submission5.data.local.provider"
        private const val FAVORITE_MOVIE = 1
        private const val FAVORITE_TV = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, FAVORITE_MOVIE)
            uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TV, FAVORITE_TV)
        }

    }

    private val filmDatabase: FilmRoomDatabase by lazy { FilmRoomDatabase.getDatabase(context as Context) }

    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = null

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 0

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 0

    override fun getType(p0: Uri): String? = null

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            FAVORITE_TV -> filmDatabase.tvShowDao().getAllProviderTvs()
            FAVORITE_MOVIE -> filmDatabase.movieDao().getAllProviderMovies()
            else -> null
        }
    }
}