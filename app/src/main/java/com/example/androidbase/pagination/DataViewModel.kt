package com.example.androidbase.pagination

import com.example.androidbase.common.presentationLayer.BaseViewModel
import com.example.androidbase.common.presentationLayer.addTo
import com.example.androidbase.pagination.data.DataGeneralRepo
import com.example.androidbase.pagination.data.errors.NoDataAvailableThrowable
import com.example.androidbase.pagination.data.errors.NoMoreOfflineDataThrowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.CompositeException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataViewModel @Inject constructor(private val dataGeneralRepo: DataGeneralRepo) : BaseViewModel<DataState>() {

    init {
        loadData()
    }

    fun refresh() {
        dataGeneralRepo.loadFromScratch()
        postState(getInitialState())
        loadData()
    }

    fun loadMore() {
        postState(
            getCurrentState().copy(
                loading = DataLoading.LOAD_MORE,
                error = null
            )
        )
        loadData()
    }

    private fun loadData() {
        dataGeneralRepo.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val newDataList = getCurrentState().dataList.toMutableList()
                    newDataList.addAll(it)
                    postState(
                        getCurrentState().copy(
                            dataList = newDataList,
                            loading = null,
                            error = null
                        )
                    )
                },
                {
                    val throwable = if (it is CompositeException)
                        it.exceptions.last()
                    else
                        it

                    postState(
                        getCurrentState().copy(
                            loading = null,
                            error = when (throwable) {
                                is NoMoreOfflineDataThrowable -> DataErrors.NO_MORE_OFFLINE_DATA
                                is NoDataAvailableThrowable -> DataErrors.NO_DATA_AVAILABLE
                                else -> DataErrors.UNKNOWN
                            }
                        )
                    )
                }
            ).addTo(compositeDisposable)
    }

    override fun getInitialState(): DataState {
        return DataState(loading = DataLoading.LOAD_FROM_SCRATCH)
    }
}