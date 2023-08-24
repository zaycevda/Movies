package com.example.movies.domain.usecase

import com.example.movies.domain.repository.FavoritesRepository

class DeleteFavoriteUseCase (private val repository: FavoritesRepository) {
    suspend fun execute(id: Long) {
        repository.deleteFavorite(id = id)
    }
}