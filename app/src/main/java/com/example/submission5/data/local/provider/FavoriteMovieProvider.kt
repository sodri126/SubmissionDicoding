package com.example.submission5.data.local.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submission5.data.local.FilmRoomDatabase
import org.koin.android.ext.android.inject

class FavoriteMovieProvider: ContentProvider() {
    companion object {
        private const val TABLE_NAME = "FAVORITE_MOVIE"
        private const val AUTHORITY = "com.example.submission5.data.local.provider"
        private const val FAVORITE_MOVIE = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_MOVIE)
        }

    }
    private val filmDatabase: FilmRoomDatabase by inject()

    override fun onCreate(): Boolean = true

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
        return if(uriMatcher.match(uri) == FAVORITE_MOVIE) filmDatabase.movieDao().getAllProviderMovies()
        else null
    }
}