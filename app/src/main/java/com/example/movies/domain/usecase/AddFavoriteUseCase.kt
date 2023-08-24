package com.example.movies.domain.usecase

import com.example.movies.domain.model.Favorite
import com.example.movies.domain.repository.FavoritesRepository

class AddFavoriteUseCase (private val repository: FavoritesRepository) {
    suspend fun execute(favorite: Favorite) {
        repository.addFavorite(favorite = favorite)
    }
}