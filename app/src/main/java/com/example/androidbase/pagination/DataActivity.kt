package com.example.androidbase.pagination

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbase.R
import com.example.androidbase.common.presentationLayer.BaseActivity
import com.example.androidbase.common.presentationLayer.EndlessRecyclerViewOnScrollListener
import kotlinx.android.synthetic.main.activity_data.*
import javax.inject.Inject

class DataActivity : BaseActivity(), DataAdapter.ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var dataViewModel: DataViewModel

    lateinit var dataAdapter: DataAdapter
    lateinit var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener

    override fun getContentResource(): Int = R.layout.activity_data

    override fun init(state: Bundle?) {
        dataViewModel = ViewModelProvider(this, viewModelFactory).get(DataViewModel::class.java)
        dataViewModel.getLiveData().observe(this, Observer {
            render(it)
        })

        dataAdapter = DataAdapter()
        dataAdapter.setItemClickListener(this)

        rv_data.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(baseContext)
        rv_data.layoutManager = linearLayoutManager
        rv_data.adapter = dataAdapter
        endlessRecyclerViewOnScrollListener = object : EndlessRecyclerViewOnScrollListener() {

            override fun getLayoutManager(): RecyclerView.LayoutManager = linearLayoutManager

            override fun onLoadMore() {
                dataViewModel.loadMore()
            }
        }
        rv_data.addOnScrollListener(endlessRecyclerViewOnScrollListener)

        swipe_layout.setOnRefreshListener {
            swipe_layout.isRefreshing = false
            dataViewModel.refresh()
        }
    }

    private fun render(dataState: DataState) {
        with(dataState) {
            if (error == null)
                dataAdapter.addData(dataList)
            else {
                Toast.makeText(
                    baseContext,
                    when (error) {
                        DataErrors.NO_DATA_AVAILABLE -> getString(R.string.msg_no_available_data)
                        DataErrors.NO_MORE_OFFLINE_DATA -> getString(R.string.msg_no_more_offline_data)
                        else -> getString(R.string.msg_unknown_error)
                    },
                    Toast.LENGTH_LONG
                ).show()
                dataAdapter.addRetryRow()
            }

            if (loadingFromScratch) {
                pb_loading.visibility = View.VISIBLE
                endlessRecyclerViewOnScrollListener.restState()
            } else
                pb_loading.visibility = View.INVISIBLE

            if (loadMore)
                dataAdapter.addLoadMoreRow()
        }
    }

    override fun onRetryClick() {
        dataViewModel.retry()
    }
}
