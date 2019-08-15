package com.example.androidbase.pagination.data

import com.example.androidbase.common.dataLayer.remote.error.UnknownThrowable
import com.example.androidbase.pagination.data.errors.NoDataAvailableThrowable
import com.example.androidbase.pagination.data.errors.NoMoreOfflineDataThrowable
import com.example.androidbase.pagination.data.local.DateLocalRepo
import com.example.androidbase.pagination.data.remote.DateRemoteRepo
import io.reactivex.Maybe
import io.reactivex.functions.Consumer
import java.io.IOException
import javax.inject.Inject

class DataGeneralRepo @Inject constructor(private val dataRemoteRepo: DateRemoteRepo,
                                          private val dataLocalRepo: DateLocalRepo) {

    var page: Int = 1

    fun loadFromScratch() {
        page = 1
    }

     fun getData(): Maybe<List<Data>> =
        dataLocalRepo.getData(page)
            .flatMap {
                if (it.isEmpty())
                    dataRemoteRepo.getData(page)
                        .doOnSuccess { remoteDataList ->
                            if (remoteDataList.isEmpty() && page == 1)
                                throw NoDataAvailableThrowable()
                            dataLocalRepo.insert(remoteDataList).subscribe()
                        }
                        .onErrorReturn { throwable -> throw when (throwable) {
                            is IOException -> NoMoreOfflineDataThrowable()
                            else -> throwable
                        } }
                        .toMaybe()
                else
                    Maybe.just(it)
            }.doOnSuccess(Consumer { if (it.isNotEmpty()) page += 1 })
}