package com.example.androidbase.pagination.data.local

import com.example.androidbase.pagination.data.Data
import com.example.androidbase.pagination.data.PAGE_LIMIT
import io.reactivex.Maybe
import javax.inject.Inject

class DateLocalRepo @Inject constructor(private val dataDao: DataDao) {


    fun insert(dataList: List<Data>) = dataDao.insert(dataList)

    fun getData(page: Int): Maybe<List<Data>> = dataDao.getData((page -1) * PAGE_LIMIT, PAGE_LIMIT)
}