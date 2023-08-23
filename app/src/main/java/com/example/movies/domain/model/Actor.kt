package com.example.movies.domain.model

data class Actor(
    val id: Long,
    val image: String,
    val name: String?,
    val character: String?
)