package com.example.androidbase.pagination.data.remote

import javax.inject.Inject

class DateRemoteRepo @Inject constructor(private val dataApi: DataApi) {

    fun getData(page: Int) = dataApi.getData(page).map { it.data }
}