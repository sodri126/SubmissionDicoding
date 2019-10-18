package com.example.moviecatalogfavorite.data.model

import android.database.Cursor
import com.example.moviecatalogfavorite.data.local.ContentProviderMovieCatalog

data class FavoriteTvShow(
    var tvShowId: Int = 0,
    var tvShowImagePath: String? = null,
    var tvShowTitle: String = "",
    var tvShowRate: Float = 0F,
    var tvShowOverview: String = "",
    var tvShowDate: String = ""
) {
    constructor(cursor: Cursor) : this() {
        this.tvShowId = ContentProviderMovieCatalog.getColumnInt(cursor, "tvshow_id")
        this.tvShowImagePath =
            ContentProviderMovieCatalog.getColumnString(cursor, "tvshow_image_path")
        this.tvShowTitle = ContentProviderMovieCatalog.getColumnString(cursor, "tvshow_title")
        this.tvShowRate = ContentProviderMovieCatalog.getColumnFloat(cursor, "tvshow_rate")
        this.tvShowOverview = ContentProviderMovieCatalog.getColumnString(cursor, "tvshow_overview")
        this.tvShowDate = ContentProviderMovieCatalog.getColumnString(cursor, "tvshow_date")
    }
}