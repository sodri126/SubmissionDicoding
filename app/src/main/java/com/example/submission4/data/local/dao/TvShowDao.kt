package com.example.submission4.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission4.data.local.entity.TvShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv_show ORDER BY tvshow_id DESC")
    suspend fun getAllTvs(): List<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShowEntity: TvShowEntity)

    @Delete
    suspend fun delete(tvShowEntity: TvShowEntity)
}