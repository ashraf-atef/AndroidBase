package com.example.androidbase.common.dataLayer.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidbase.pagination.data.Data
import com.example.androidbase.pagination.data.local.DataDao


@Database(entities = [Data::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {

        lateinit var instance: LocalDatabase
        fun getInstance(context: Context): LocalDatabase {
            synchronized(LocalDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java, "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                return instance
            }
        }
    }
}