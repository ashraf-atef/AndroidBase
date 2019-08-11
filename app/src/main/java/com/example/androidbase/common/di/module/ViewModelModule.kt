package com.example.androidbase.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidbase.pagination.DataViewModel
import com.example.androidbase.common.presentationLayer.ViewModelFactory
import com.example.androidbase.common.presentationLayer.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel::class)
    abstract fun bindDataViewModel(dataViewModel: DataViewModel): ViewModel

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}