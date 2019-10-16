package com.example.moviecatalogfavorite.data.local

import android.database.Cursor
import android.net.Uri

object ContentProviderMovieCatalog {
    private const val TABLE_NAME = "FAVORITE_MOVIE"
    private const val SCHEMA = "content"
    private const val AUTHORITY = "com.example.submission5.data.local.provider"

    val CONTENT_URI: Uri = Uri.Builder()
                            .scheme(SCHEMA)
                            .appendPath(TABLE_NAME)
                            .authority(AUTHORITY)
                            .build()


    fun getColumnString(cursor: Cursor, columnName: String): String = cursor.getString(cursor.getColumnIndex(columnName))

    fun getColumnInt(cursor: Cursor, columnName: String): Int = cursor.getInt(cursor.getColumnIndex(columnName))

    fun getColumnFloat(cursor: Cursor, columnName: String): Float = cursor.getFloat(cursor.getColumnIndex(columnName))
}