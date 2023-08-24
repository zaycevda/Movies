package com.example.movies.data.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.db.dao.MovieDao
import com.example.movies.data.db.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1
)
abstract class RoomDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}