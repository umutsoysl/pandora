package com.laks.tvseries.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_media_table")
data class DBMediaEntity (
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "image")
        var image: String = "",
        @ColumnInfo(name = "rating")
        var rating: Double? = 0.0,
        @ColumnInfo(name = "is_movie")
        var isMovie: Boolean = false,
        @ColumnInfo(name = "is_favorite")
        var isFavorite: Boolean = false,
        @ColumnInfo(name = "is_watched")
        var isWatched: Boolean = false,
        @ColumnInfo(name = "process_date")
        var processDate: Long = 20210100,
        @ColumnInfo(name = "insert_date")
        var insertDate: String = ""
)