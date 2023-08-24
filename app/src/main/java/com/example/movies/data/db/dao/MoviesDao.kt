package com.example.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.db.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movieEntities: List<MovieEntity>)
    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieEntity>
}