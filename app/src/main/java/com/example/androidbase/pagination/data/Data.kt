package com.example.androidbase.pagination.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Data")
data class Data(@PrimaryKey val id:Long,
    val first_name:String,
    val last_name: String,
    val avatar: String,
    val email: String)