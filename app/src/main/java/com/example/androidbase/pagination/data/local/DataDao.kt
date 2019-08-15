package com.example.androidbase.pagination.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidbase.pagination.data.Data
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<Data>): Completable

    @Query("SELECT * FROM Data LIMIT :index, :limit")
    fun getData(index: Int, limit: Int) : Maybe<List<Data>>
}