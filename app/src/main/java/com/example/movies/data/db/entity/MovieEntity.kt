package com.example.movies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("preview")
    val preview: String,
    @ColumnInfo("title")
    val title: String?,
    @ColumnInfo("rating")
    val rating: Double
)