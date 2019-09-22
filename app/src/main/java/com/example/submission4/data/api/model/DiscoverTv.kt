package com.example.submission4.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date
data class DiscoverTv(@SerializedName("original_name") val originalName: String,
                      @SerializedName("genre_ids") val genresId: ArrayList<Int>,
                      @SerializedName("name") val name: String,
                      @SerializedName("popularity") val popularity: Float,
                      @SerializedName("origin_country") val originCountry: ArrayList<String>,
                      @SerializedName("vote_count") val voteCount: Int,
                      @SerializedName("first_air_date") val firstAirDate: Date,
                      @SerializedName("backdrop_path") val backdropPath: String?,
                      @SerializedName("original_language") val originalLanguage: String,
                      @SerializedName("id") val id: Int,
                      @SerializedName("vote_average") val voteAverage: Float,
                      @SerializedName("overview") val overview: String,
                      @SerializedName("poster_path") val posterPath: String?,
                      var isFavorite: Boolean = false)