package com.example.androidbase.pagination.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("users")
    fun getData(@Query("page") page: Int): Single<DataResponseDto>
}