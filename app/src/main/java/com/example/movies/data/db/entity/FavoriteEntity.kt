package com.example.movies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val FAVORITES = "favorites"

@Entity(FAVORITES)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID)
    val id: Long,
    @ColumnInfo(PREVIEW)
    val preview: String,
    @ColumnInfo(TITLE)
    val title: String?,
    @ColumnInfo(RATING)
    val rating: Double?
) {
    private companion object {
        private const val ID = "id"
        private const val PREVIEW = "preview"
        private const val TITLE = "title"
        private const val RATING = "rating"
    }
}