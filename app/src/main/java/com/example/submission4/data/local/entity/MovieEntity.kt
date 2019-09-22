package com.example.submission4.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(@PrimaryKey @ColumnInfo(name = "movie_id") val movieId: Int,
                       @ColumnInfo(name = "movie_title") val movieTitle: String,
                       @ColumnInfo(name = "movie_date") val movieDate: String,
                       @ColumnInfo(name = "movie_overview") val movieOverview: String,
                       @ColumnInfo(name = "movie_rate") val movieRate: Float,
                       @ColumnInfo(name = "movie_image_path") val movieImagePath: String?,
                       var isFavorite: Boolean = false)