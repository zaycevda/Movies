package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("kinopoiskId")
    val id: Long,
    @SerializedName("posterUrlPreview")
    val preview: String,
    @SerializedName("nameRu")
    val title: String?,
    @SerializedName("ratingKinopoisk")
    val rating: Double
)