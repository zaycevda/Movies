package com.example.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.db.entity.FavoriteEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()
    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: Long)
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<FavoriteEntity>
}