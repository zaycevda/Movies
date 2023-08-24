package com.example.movies.domain.usecase

import com.example.movies.domain.repository.FavoritesRepository

class GetFavoritesUseCase(private val repository: FavoritesRepository) {
    suspend fun execute() = repository.getFavorites()
}