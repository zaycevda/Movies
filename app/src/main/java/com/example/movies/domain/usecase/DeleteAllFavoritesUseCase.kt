package com.example.movies.domain.usecase

import com.example.movies.domain.repository.FavoritesRepository

class DeleteAllFavoritesUseCase(private val repository: FavoritesRepository) {
    suspend fun execute() {
        repository.deleteAllFavorites()
    }
}