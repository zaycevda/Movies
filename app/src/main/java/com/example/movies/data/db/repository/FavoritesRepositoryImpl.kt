package com.example.movies.data.db.repository

import com.example.movies.data.db.db.RoomDb
import com.example.movies.data.util.toFavorite
import com.example.movies.data.util.toFavoriteEntity
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(db: RoomDb) : FavoritesRepository {

    private val dao = db.favoritesDao()

    override suspend fun addFavorite(favorite: Favorite) {
        val favoriteEntity = favorite.toFavoriteEntity()
        dao.addFavorite(favoriteEntity = favoriteEntity)
    }

    override suspend fun deleteAllFavorites() {
        dao.deleteAllFavorites()
    }

    override suspend fun deleteFavorite(id: Long) {
        dao.deleteFavorite(id = id)
    }

    override suspend fun getFavorites(): List<Favorite> {
        return dao.getFavorites().map { favoriteEntity -> favoriteEntity.toFavorite() }
    }
}