package com.example.movies.domain.repository

import com.example.movies.domain.model.Favorite

interface FavoritesRepository {
    suspend fun addFavorite(favorite: Favorite)
    suspend fun deleteAllFavorites()
    suspend fun deleteFavorite(id: Long)
    suspend fun getFavorites(): List<Favorite>
}