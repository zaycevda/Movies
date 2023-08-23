package com.example.movies.domain.model

data class MovieDetail(
    val id: Long,
    val poster: String,
    val title: String?,
    val rating: Double?,
    val description: String?,
    val premiere: Int?,
    val actors: List<Actor>,
    val video: String?
)