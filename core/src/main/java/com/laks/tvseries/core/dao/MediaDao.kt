package com.laks.tvseries.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Update
import com.laks.tvseries.core.data.db.DBMediaEntity

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DBMediaEntity?)

    @Update
    fun update(vararg note: DBMediaEntity)

    @Query("DELETE FROM favorite_media_table WHERE is_movie = :isMovie")
    fun deleteAll(isMovie: Boolean)

    @Query("SELECT * from favorite_media_table ORDER BY process_date DESC")
    fun getAllData(): List<DBMediaEntity>?

    @Query("SELECT * from favorite_media_table WHERE id = :mediaID")
    fun findMedia(mediaID: Long): DBMediaEntity?

    @Query("SELECT * from favorite_media_table WHERE is_watched = :isWatched and is_movie = :isMovie ORDER BY process_date DESC")
    fun getWatchList(isWatched: Boolean, isMovie: Boolean): List<DBMediaEntity>?

    @Query("SELECT * from favorite_media_table WHERE is_favorite = :isFavorite and is_movie = :isMovie ORDER BY process_date DESC")
    fun getFavoriteList(isFavorite: Boolean, isMovie: Boolean): List<DBMediaEntity>?

    @Query("delete from favorite_media_table WHERE id = :media_ID")
    fun deleteMedia(media_ID: Long)

    @Delete
    fun deleteData(data: DBMediaEntity)
}