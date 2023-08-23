package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("items")
    val movies: List<MovieModel>
)