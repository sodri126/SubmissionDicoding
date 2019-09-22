package com.example.submission4.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission4.data.local.dao.MovieDao
import com.example.submission4.data.local.dao.TvShowDao
import com.example.submission4.data.local.entity.MovieEntity
import com.example.submission4.data.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1)
abstract class FilmRoomDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: FilmRoomDatabase? = null

        fun getDatabase(context: Context): FilmRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmRoomDatabase::class.java,
                    "film_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}