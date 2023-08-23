package com.example.movies.domain.model

data class Staff(
    val id: Long,
    val image: String,
    val name: String?,
    val character: String?,
    val profession: String
)