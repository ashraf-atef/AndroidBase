package com.example.androidbase.pagination

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidbase.R
import com.example.androidbase.common.presentationLayer.BaseActivity
import javax.inject.Inject

class DataActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var dataViewModel: DataViewModel

    override fun getContentResource(): Int = R.layout.activity_data

    override fun init(state: Bundle?) {
        dataViewModel =  ViewModelProvider(this, viewModelFactory).get(DataViewModel::class.java)
        dataViewModel.getLiveData().observe(this, Observer {
            Log.d("DATA", it.dataList.toString())
        })
    }

}
