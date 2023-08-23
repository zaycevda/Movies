package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("total")
    val count: Int,
    @SerializedName("items")
    val videos: List<VideoModel>
)