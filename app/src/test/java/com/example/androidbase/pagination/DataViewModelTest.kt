package com.example.androidbase.pagination

import androidx.lifecycle.Observer
import com.example.androidbase.BaseTest
import com.example.androidbase.pagination.data.Data
import com.example.androidbase.pagination.data.DataGeneralRepo
import com.example.androidbase.pagination.data.errors.NoDataAvailableThrowable
import com.example.androidbase.pagination.data.errors.NoMoreOfflineDataThrowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit


class DataViewModelTest : BaseTest() {

    lateinit var dataViewModel: DataViewModel
    @Mock
    lateinit var dataGeneralRepo: DataGeneralRepo
    @Mock
    lateinit var observer: Observer<DataState>
    lateinit var inOrder: InOrder
    lateinit var dataList: List<Data>

    override fun setup() {
        super.setup()
        dataList = listOf()
        // We call getData() inside the constructor of view model so we need to mock this function to avoid NPE
        `when`(dataGeneralRepo.getData()).then { Maybe.just(dataList) }
        dataViewModel = DataViewModel(dataGeneralRepo)
        dataViewModel.getLiveData().observeForever(observer)
        inOrder = inOrder(observer)
       `create instance from viewModel WHEN load data return a list EXPECT pushing initial state then data state`()
    }

    fun `create instance from viewModel WHEN load data return a list EXPECT pushing initial state then data state`() {
        Thread.sleep(500)
        inOrder.verify(observer).onChanged(dataViewModel.getInitialState())
        inOrder.verify(observer).onChanged(
            dataViewModel.getCurrentState().copy(
                dataList = dataList,
                loading = null,
                error = null
            )
        )
    }

    @Test
    fun `refresh WHEN load data return a list EXPECT pushing initial state then data state`() {
        dataViewModel.refresh()
        Thread.sleep(500)
        inOrder.verify(observer).onChanged(dataViewModel.getInitialState())
        inOrder.verify(observer).onChanged(
            dataViewModel.getCurrentState().copy(
                dataList = dataList,
                loading = null,
                error = null
            )
        )
    }

    @Test
    fun `load more WHEN load data return a list EXPECT pushing initial state then data state`() {
        dataViewModel.loadMore()
        Thread.sleep(500)
        inOrder.verify(observer).onChanged( dataViewModel.getCurrentState().copy(
            loading = DataLoading.LOAD_MORE,
            error = null
        ))
        inOrder.verify(observer).onChanged(
            dataViewModel.getCurrentState().copy(
                dataList = dataList,
                loading = null,
                error = null
            )
        )
    }

    @Test
    fun `load more WHEN load data return a no more data offline EXPECT pushing initial state then data state`() {
       `when`(dataGeneralRepo.getData()).then { Maybe.error<Any>(NoMoreOfflineDataThrowable()) }

        dataViewModel.loadMore()
        Thread.sleep(500)
        inOrder.verify(observer).onChanged( dataViewModel.getCurrentState().copy(
            loading = DataLoading.LOAD_MORE,
            error = null
        ))
        inOrder.verify(observer).onChanged(
            dataViewModel.getCurrentState().copy(
                loading = null,
                error = DataErrors.NO_MORE_OFFLINE_DATA
            )
        )
    }

    @Test
    fun `load more WHEN load data return a no data available EXPECT pushing initial state then data state`() {
        `when`(dataGeneralRepo.getData()).then { Maybe.error<Any>(NoDataAvailableThrowable()) }
        dataViewModel.loadMore()
        Thread.sleep(500)
        inOrder.verify(observer).onChanged( dataViewModel.getCurrentState().copy(
            loading = DataLoading.LOAD_MORE,
            error = null
        ))
        inOrder.verify(observer).onChanged(
            dataViewModel.getCurrentState().copy(
                loading = null,
                error = DataErrors.NO_DATA_AVAILABLE
            )
        )
    }
}