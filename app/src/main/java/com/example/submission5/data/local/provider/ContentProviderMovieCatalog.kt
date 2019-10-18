package com.example.submission5.data.local.provider

import android.database.Cursor
import android.net.Uri

object ContentProviderMovieCatalog {
    private const val TABLE_FAVORITE_MOVIE = "FAVORITE_MOVIE"
    private const val TABLE_FAVORITE_TV = "FAVORITE_TV"
    private const val SCHEMA = "content"
    private const val AUTHORITY = "com.example.submission5.data.local.provider"

    val CONTENT_URI_FAVORITE_MOVIE: Uri = Uri.Builder()
        .scheme(SCHEMA)
        .appendPath(TABLE_FAVORITE_MOVIE)
        .authority(AUTHORITY)
        .build()

    val CONTENT_URI_FAVORITE_TV: Uri = Uri.Builder()
        .scheme(SCHEMA)
        .appendPath(TABLE_FAVORITE_TV)
        .authority(AUTHORITY)
        .build()


    fun getColumnString(cursor: Cursor, columnName: String): String =
        cursor.getString(cursor.getColumnIndex(columnName))

    fun getColumnInt(cursor: Cursor, columnName: String): Int =
        cursor.getInt(cursor.getColumnIndex(columnName))
}