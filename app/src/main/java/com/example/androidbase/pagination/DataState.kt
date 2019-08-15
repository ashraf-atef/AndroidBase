package com.example.androidbase.pagination

import com.example.androidbase.pagination.data.Data

data class DataState(
    var dataList: List<Data> = listOf(),
    val loadingFromScratch: Boolean = false,
    val loadMore: Boolean = false,
    val error: DataErrors? = null
)
enum class DataErrors {
    NO_DATA_AVAILABLE, NO_MORE_OFFLINE_DATA, UNKNOWN
}