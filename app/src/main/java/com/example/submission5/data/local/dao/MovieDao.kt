package com.example.submission5.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission5.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY movie_id DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie ORDER BY movie_id DESC")
    fun getAllProviderMovies(): Cursor

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    suspend fun getMovie(movieId: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("DELETE FROM movie WHERE movie_id = :movieId")
    suspend fun delete(movieId: Int)
}