package com.example.submission4.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DetailTv(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") val firstAirDate: Date,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("homepage") val homePage: String,
    @SerializedName("name") val name: String,
    @SerializedName("number_of_episodes") val numberOfEpisode: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("status") val status: String,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    var isFavorite: Boolean = false
)