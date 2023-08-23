package com.example.movies.domain.model

data class Movie(
    val id: Long,
    val preview: String,
    val title: String?,
    val rating: Double
)