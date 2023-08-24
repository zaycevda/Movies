package com.example.movies.data.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.db.dao.FavoritesDao
import com.example.movies.data.db.dao.MoviesDao
import com.example.movies.data.db.entity.FavoriteEntity
import com.example.movies.data.db.entity.MovieEntity

@Database(
    entities = [
        FavoriteEntity::class,
        MovieEntity::class
    ],
    version = 1
)
abstract class RoomDb : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun moviesDao(): MoviesDao
}