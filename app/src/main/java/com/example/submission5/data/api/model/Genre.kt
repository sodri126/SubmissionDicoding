package com.example.submission5.data.api.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val genreId: Int,
    @SerializedName("name") val genreName: String
)