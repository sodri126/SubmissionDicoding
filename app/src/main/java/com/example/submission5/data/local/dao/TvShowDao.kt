package com.example.submission5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission5.data.local.entity.TvShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv_show ORDER BY tvshow_id DESC")
    suspend fun getAllTvs(): List<TvShowEntity>

    @Query("SELECT * FROM tv_show WHERE tvshow_id = :tvShowId")
    suspend fun getMovie(tvShowId: Int): TvShowEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShowEntity: TvShowEntity)

    @Query("DELETE FROM tv_show WHERE tvshow_id = :tvShowId")
    suspend fun delete(tvShowId: Int)
}