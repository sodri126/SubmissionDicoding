package com.example.submission5.data.api.model

import com.google.gson.annotations.SerializedName

data class GeneralResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: ArrayList<T>
)