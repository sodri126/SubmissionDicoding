package com.example.submission4.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class DiscoverMovie(@SerializedName("vote_count") val voteCount: Int,
                               @SerializedName("id") val id: Int,
                               @SerializedName("video") val video: Boolean,
                               @SerializedName("title") val title: String,
                               @SerializedName("popularity") val popularity: Float,
                               @SerializedName("poster_path") val posterPath: String?,
                               @SerializedName("original_language") val originalLanguage: String,
                               @SerializedName("original_title") val originalTitle: String,
                               @SerializedName("genres_id") val genresId: ArrayList<Int>,
                               @SerializedName("backdrop_path") val backdropPath: String?,
                               @SerializedName("adult") val adult: Boolean,
                               @SerializedName("overview") val overview: String,
                               @SerializedName("release_date") val releaseDate: Date,
                               @SerializedName("vote_average") val voteAverage: Float,
                               var isFavorite: Boolean = false)