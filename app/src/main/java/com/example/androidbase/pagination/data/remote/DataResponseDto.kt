package com.example.androidbase.pagination.data.remote

import com.example.androidbase.pagination.data.Data

class DataResponseDto(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val total: Int,
    val data: List<Data>)