package com.example.androidbase.pagination

import com.example.androidbase.pagination.data.Data

data class DataState(
    var dataList: List<Data> = listOf(),
    val loading: DataLoading? = null,
    val error: DataErrors? = null
)
enum class DataErrors {
    NO_DATA_AVAILABLE, NO_MORE_OFFLINE_DATA, UNKNOWN
}

enum class DataLoading {
    LOAD_MORE, LOAD_FROM_SCRATCH
}