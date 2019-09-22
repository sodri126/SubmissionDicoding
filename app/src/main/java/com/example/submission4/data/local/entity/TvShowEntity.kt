package com.example.submission4.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show")
data class TvShowEntity(@PrimaryKey @ColumnInfo(name = "tvshow_id") val tvShowId: Int,
                       @ColumnInfo(name = "tvshow_title") val tvShowTitle: String,
                       @ColumnInfo(name = "tvshow_date") val tvShowDate: String,
                       @ColumnInfo(name = "tvshow_overview") val tvShowOverview: String,
                       @ColumnInfo(name = "tvshow_rate") val tvShowRate: Float,
                       @ColumnInfo(name = "tvshow_image_path") val tvShowImagePath: String,
                       var isFavorite: Boolean = false)