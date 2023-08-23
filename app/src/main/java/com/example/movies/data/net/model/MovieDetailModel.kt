package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
    @SerializedName("kinopoiskId")
    val id: Long,
    @SerializedName("posterUrlPreview")
    val poster: String,
    @SerializedName("nameRu")
    val title: String?,
    @SerializedName("ratingKinopoisk")
    val rating: Double,
    @SerializedName("description")
    val description: String?,
    @SerializedName("year")
    val premiere: Int?
)