package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("url")
    val url: String,
    @SerializedName("site")
    val site: String
)