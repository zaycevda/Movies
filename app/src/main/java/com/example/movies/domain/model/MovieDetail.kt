package com.example.movies.domain.model

data class MovieDetail(
    val id: Long,
    val poster: String,
    val title: String?,
    val rating: Double?,
    val description: String?,
    val premiere: Int?,
    val staff: List<Staff>,
    val video: String?
)