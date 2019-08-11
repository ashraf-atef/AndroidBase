package com.example.androidbase.pagination

import com.example.androidbase.common.presentationLayer.BaseViewModel
import com.example.androidbase.common.presentationLayer.addTo
import com.example.androidbase.pagination.data.remote.DateRemoteRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataViewModel
@Inject constructor(private val dateRemoteRepo: DateRemoteRepo) : BaseViewModel<DataState>() {

    init {
        dateRemoteRepo.getData(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            Consumer {
                postState(getCurrentState().copy(dataList = it))
            }
        ).addTo(compositeDisposable)
    }

    override fun getInitialState(): DataState {
        return DataState()
    }
}